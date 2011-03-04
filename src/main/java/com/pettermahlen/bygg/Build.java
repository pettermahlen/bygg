/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;
import com.pettermahlen.bygg.execution.Execution;
import com.pettermahlen.bygg.execution.TargetDAG;
import com.pettermahlen.bygg.execution.TargetNode;

import java.util.*;
import java.util.concurrent.ExecutionException;
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

    public Build(ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties, ExecutorService executorService) {
        this.byggConfiguration = byggConfiguration;
        this.properties = properties;
        this.executorService = executorService;
    }

    public void build(List<String> targetNames) {
        TargetDAG totalDag = byggConfiguration.getTargetDAG();

        Set<TargetNode> sinkNodes = figureOutSinkNodes(totalDag, targetNames);

        // OK, now set up the executions
        for (TargetNode targetNode : sinkNodes) {
            recursivelyExecute(targetNode);
        }

        // TODO: should probably have some kind of factory here instead of possibly a singleton
        executorService.shutdown();
    }

    private Set<TargetNode> figureOutSinkNodes(TargetDAG totalDag, List<String> targetNames) {
        // find the target nodes that correspond to the names
        // remove any target nodes that are included in the set of predecessors of any other target node
        // return the remainder

        Set<TargetNode> namedTargets = new HashSet<TargetNode>();

        for (String targetName : targetNames) {
            namedTargets.add(totalDag.findNode(targetName));
        }

        // this algorithm has O(n*n*n) complexity, but the number of expected targets is low, so I don't mind for now
        for (Iterator<TargetNode> iterator = namedTargets.iterator(); iterator.hasNext() ; ) {
            TargetNode nodeToPossiblyRemove = iterator.next();

            if (shouldRemove(namedTargets, nodeToPossiblyRemove)) {
                iterator.remove();
            }
        }

        return namedTargets;
    }

    private boolean shouldRemove(Set<TargetNode> namedTargets, TargetNode nodeToPossiblyRemove) {
        for (TargetNode node : namedTargets) {
            if (isInPredecessors(nodeToPossiblyRemove, node.getPredecessors())) {
                return true;
            }
        }
        
        return false;
    }

    private boolean isInPredecessors(TargetNode nodeToPossiblyRemove, Set<TargetNode> predecessors) {
        for (TargetNode node : predecessors) {
            if (node.equals(nodeToPossiblyRemove)) {
                return true;
            }

            if (isInPredecessors(nodeToPossiblyRemove, node.getPredecessors())) {
                return true;
            }
        }

        return false;
    }

    private Future<?> recursivelyExecute(TargetNode targetNode) {
        return executorService.submit(createRunnable(targetNode));
    }

    private Runnable createRunnable(final TargetNode targetNode) {
        final Set<Future<?>> futuresForNode = new HashSet<Future<?>>();

        for (TargetNode predecessor : targetNode.getPredecessors()) {
            futuresForNode.add(recursivelyExecute(predecessor));
        }

        return new Runnable() {
            @Override
            public void run() {
                for (Future<?> future : futuresForNode) {
                    try {
                        future.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }

                targetNode.getExecutor().execute();
            }
        };
    }

}
