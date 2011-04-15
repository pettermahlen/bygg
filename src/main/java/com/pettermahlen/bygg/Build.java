/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;
import com.pettermahlen.bygg.execution.NodeCallableFactory;
import com.pettermahlen.bygg.execution.TargetDAG;
import com.pettermahlen.bygg.execution.TargetNode;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class Build {
    private final ByggConfiguration byggConfiguration;
    private final Map<ByggProperty, String> properties;
    private final ExecutorService executorService;
    private final NodeCallableFactory nodeCallableFactory;

    public Build(ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties, ExecutorService executorService, NodeCallableFactory nodeCallableFactory) {
        this.byggConfiguration = Preconditions.checkNotNull(byggConfiguration, "byggConfiguration");
        this.properties = ImmutableMap.copyOf(properties);
        this.executorService = Preconditions.checkNotNull(executorService, "executorService");
        this.nodeCallableFactory = Preconditions.checkNotNull(nodeCallableFactory, "nodeCallableFactory");
    }

    public void build(List<String> targetNames) {
        TargetDAG totalDag = byggConfiguration.getTargetDAG();

        Set<TargetNode> sinkNodes = figureOutSinkNodes(totalDag, targetNames);

        Map<TargetNode, Future<?>> executedNodes = new HashMap<TargetNode, Future<?>>();

        // OK, now set up the executions
        for (TargetNode targetNode : sinkNodes) {
            recursivelyExecute(targetNode, executedNodes);
        }

        // TODO: should maybe be blocking on the futures

        // TODO: should probably have some kind of factory for the exec service instead of possibly a singleton, so this guy manages the whole life cycle of the service
        executorService.shutdown();
    }

    private Set<TargetNode> figureOutSinkNodes(TargetDAG totalDag, List<String> targetNames) {
        Set<TargetNode> namedTargets = new HashSet<TargetNode>();

        for (String targetName : targetNames) {
            namedTargets.add(totalDag.findNode(targetName));
        }

        return namedTargets;
    }

    private Future<?> recursivelyExecute(TargetNode targetNode, Map<TargetNode, Future<?>> executedNodes) {
        if (executedNodes.containsKey(targetNode)) {
            return executedNodes.get(targetNode);
        }

        Future<?> future = executorService.submit(createRunnable(targetNode, executedNodes));
        executedNodes.put(targetNode, future);

        return future;
    }

    private Callable<?> createRunnable(final TargetNode targetNode, Map<TargetNode, Future<?>> executedNodes) {
        final Set<Future<?>> futuresForNode = new HashSet<Future<?>>();

        for (TargetNode predecessor : targetNode.getPredecessors()) {
            futuresForNode.add(recursivelyExecute(predecessor, executedNodes));
        }

        return nodeCallableFactory.createCallable(targetNode.getExecutor(), futuresForNode);
    }

}
