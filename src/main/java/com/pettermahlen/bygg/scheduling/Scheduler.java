/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.pettermahlen.bygg.BuildResult;
import com.pettermahlen.bygg.BuildStep;

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
    public Iterable<DagNode<BuildStep>> schedule(Collection<? extends BuildStep> buildSteps) {
        // first, go through all build steps and establish which steps need to run before

        Multimap<BuildStep, BuildStep> mustRunAfter = ArrayListMultimap.create();

        for (BuildStep step : buildSteps) {
            for (BuildStep stepToCheck : buildSteps) {
                if (step == stepToCheck) {
                    continue;
                }

                if (mustRunAfter(step, stepToCheck)) {
                    mustRunAfter.put(step, stepToCheck);
                }
            }
        }

        Set<DagNode<BuildStep>> allNodes = createRecursively(new HashSet<BuildStep>(buildSteps), new HashSet<DagNode<BuildStep>>(), mustRunAfter);

        Set<DagNode<BuildStep>> nodesWithPredecessors = findNodesWithPredecessors(allNodes);

        return Sets.difference(allNodes, nodesWithPredecessors);
    }

    private Set<DagNode<BuildStep>> createRecursively(Set<? extends BuildStep> buildSteps, HashSet<DagNode<BuildStep>> allNodes, Multimap<BuildStep, BuildStep> mustRunAfter) {
        int sizeBefore = buildSteps.size();

        for (Iterator<? extends BuildStep> iterator = buildSteps.iterator() ; iterator.hasNext() ; ) {
            BuildStep step = iterator.next();
            DagNode<BuildStep> node = createNode(step, allNodes, mustRunAfter);

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

    private DagNode<BuildStep> createNode(BuildStep step, HashSet<DagNode<BuildStep>> allNodes, Multimap<BuildStep, BuildStep> mustRunAfter) {
        if (!mustRunAfter.containsKey(step)) {
            return new DagNode<BuildStep>(Collections.<DagNode<BuildStep>>emptyList(), step);
        }

        HashSet<DagNode<BuildStep>> successorNodes = new HashSet<DagNode<BuildStep>>();

        for (BuildStep successor : mustRunAfter.get(step)) {
            DagNode<BuildStep> successorNode = findNodeForStep(successor, allNodes);

            if (successorNode == null) {
                return null;
            }

            successorNodes.add(successorNode);
        }

        return new DagNode<BuildStep>(successorNodes, step);
    }

    private DagNode<BuildStep> findNodeForStep(BuildStep step, HashSet<DagNode<BuildStep>> nodes) {
        for (DagNode<BuildStep> node : nodes) {
            if (node.getPayload().equals(step)) {
                return node;
            }
        }

        return null;
    }


    private boolean mustRunAfter(BuildStep step, BuildStep stepToCheck) {
        return stepToCheckNeedsInputFrom(step, stepToCheck) ||
               stepToCheckRequiresPredecessor(step, stepToCheck) ||
               stepRequiresSuccessor(step, stepToCheck);
    }

    private boolean stepToCheckNeedsInputFrom(BuildStep step, BuildStep stepToCheck) {
        return !Sets.intersection(step.outputs(), stepToCheck.inputs()).isEmpty();
    }

    private boolean stepToCheckRequiresPredecessor(BuildStep step, BuildStep stepToCheck) {
        return stepToCheck.predecessors().contains(step);
    }

    private boolean stepRequiresSuccessor(BuildStep step, BuildStep stepToCheck) {
        return step.successors().contains(stepToCheck);
    }

    private Set<DagNode<BuildStep>> findNodesWithPredecessors(Set<DagNode<BuildStep>> allNodes) {
        HashSet<DagNode<BuildStep>> result = new HashSet<DagNode<BuildStep>>();

        for (DagNode<BuildStep> node : allNodes) {
            if (nodeExistsAsSuccessor(node, allNodes)) {
                result.add(node);
            }
        }

        return result;
    }

    private boolean nodeExistsAsSuccessor(DagNode<BuildStep> node, Set<DagNode<BuildStep>> allNodes) {
        for (DagNode<BuildStep> possiblePredecessor : allNodes) {
            if (possiblePredecessor.getEdges().contains(node)) {
                return true;
            }
        }

        return false;
    }


}
