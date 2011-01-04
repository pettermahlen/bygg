/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * TODO: document this class!
 * TODO: optimise clarity and possibly performance
 *
 * @author Petter Måhlén
 * @since Dec 27, 2010
 */
public class Scheduler {

    // TODO: make this API more flexible using generics correctly
    public Iterable<DagNode<Schedulable>> schedule(Collection<? extends Schedulable> buildSteps) {
        // first, go through all build steps and establish which steps need to run before

        Multimap<Schedulable, Schedulable> mustRunAfter = ArrayListMultimap.create();

        for (Schedulable step : buildSteps) {
            for (Schedulable stepToCheck : buildSteps) {
                if (step == stepToCheck) {
                    continue;
                }

                if (mustRunAfter(step, stepToCheck)) {
                    mustRunAfter.put(step, stepToCheck);
                }
            }
        }

        Set<DagNode<Schedulable>> allNodes = createRecursively(new HashSet<Schedulable>(buildSteps), new HashSet<DagNode<Schedulable>>(), mustRunAfter);

        Set<DagNode<Schedulable>> nodesWithPredecessors = findNodesWithPredecessors(allNodes);

        return Sets.difference(allNodes, nodesWithPredecessors);
    }

    private Set<DagNode<Schedulable>> createRecursively(Set<? extends Schedulable> buildSteps, HashSet<DagNode<Schedulable>> allNodes, Multimap<Schedulable, Schedulable> mustRunAfter) {
        int sizeBefore = buildSteps.size();

        for (Iterator<? extends Schedulable> iterator = buildSteps.iterator() ; iterator.hasNext() ; ) {
            Schedulable step = iterator.next();
            DagNode<Schedulable> node = createNode(step, allNodes, mustRunAfter);

            if (node != null) {
                allNodes.add(node);
                iterator.remove();
            }
        }

        if (buildSteps.size() == sizeBefore) {
            throw new RuntimeException("couldn't create any nodes");
        }

        if (buildSteps.isEmpty()) {
            return allNodes;
        }
        else {
            return createRecursively(buildSteps, allNodes, mustRunAfter);
        }
    }

    private DagNode<Schedulable> createNode(Schedulable step, HashSet<DagNode<Schedulable>> allNodes, Multimap<Schedulable, Schedulable> mustRunAfter) {
        if (!mustRunAfter.containsKey(step)) {
            return new DagNode<Schedulable>(Collections.<DagNode<Schedulable>>emptyList(), step);
        }

        HashSet<DagNode<Schedulable>> successorNodes = new HashSet<DagNode<Schedulable>>();

        for (Schedulable successor : mustRunAfter.get(step)) {
            DagNode<Schedulable> successorNode = findNodeForStep(successor, allNodes);

            if (successorNode == null) {
                return null;
            }

            successorNodes.add(successorNode);
        }

        return new DagNode<Schedulable>(successorNodes, step);
    }

    private DagNode<Schedulable> findNodeForStep(Schedulable step, HashSet<DagNode<Schedulable>> nodes) {
        for (DagNode<Schedulable> node : nodes) {
            if (node.getPayload().equals(step)) {
                return node;
            }
        }

        return null;
    }


    private boolean mustRunAfter(Schedulable step, Schedulable stepToCheck) {
        return stepToCheckNeedsInputFrom(step, stepToCheck) ||
               stepToCheckRequiresPredecessor(step, stepToCheck) ||
               stepRequiresSuccessor(step, stepToCheck);
    }

    private boolean stepToCheckNeedsInputFrom(Schedulable step, Schedulable stepToCheck) {
        return !Sets.intersection(step.outputs(), stepToCheck.inputs()).isEmpty();
    }

    private boolean stepToCheckRequiresPredecessor(Schedulable step, Schedulable stepToCheck) {
        return stepToCheck.predecessors().contains(step);
    }

    private boolean stepRequiresSuccessor(Schedulable step, Schedulable stepToCheck) {
        return step.successors().contains(stepToCheck);
    }

    private Set<DagNode<Schedulable>> findNodesWithPredecessors(Set<DagNode<Schedulable>> allNodes) {
        HashSet<DagNode<Schedulable>> result = new HashSet<DagNode<Schedulable>>();

        for (DagNode<Schedulable> node : allNodes) {
            if (nodeExistsAsSuccessor(node, allNodes)) {
                result.add(node);
            }
        }

        return result;
    }

    private boolean nodeExistsAsSuccessor(DagNode<Schedulable> node, Set<DagNode<Schedulable>> allNodes) {
        for (DagNode<Schedulable> possiblePredecessor : allNodes) {
            if (possiblePredecessor.getEdges().contains(node)) {
                return true;
            }
        }

        return false;
    }


}
