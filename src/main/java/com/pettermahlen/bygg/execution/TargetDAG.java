/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.pettermahlen.bygg.ByggException;
import com.pettermahlen.bygg.ByggTargetExecutor;

import java.util.Collections;
import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class TargetDAG {
    public static final TargetDAG DEFAULT = new TargetDAG(Collections.<TargetNode>emptyList())
            .add("assembleMain").executor(new DummyExecutor("assembleMain"))
            .add("compile").executor(new DummyExecutor("compile")).requires("assembleMain")
            .add("assembleTest").executor(new DummyExecutor("assembleTest"))
            .add("compileTest").executor(new DummyExecutor("compileTest")).requires("assembleTest", "compile")
            .add("test").executor(new DummyExecutor("test")).requires("compile", "compileTest")
            .build();

    private final Set<TargetNode> targetNodes;

    public TargetDAG(Iterable<TargetNode> targetNodes) {
        this.targetNodes = ImmutableSet.copyOf(targetNodes);
    }

    public TargetDAG.Builder add(String name) {
        return new Builder(name);
    }

    public TargetDAG add(TargetNode node) {
        ImmutableSet.Builder<TargetNode> builder = ImmutableSet.builder();
        builder.addAll(targetNodes);
        builder.add(node);

        return new TargetDAG(builder.build());
    }

    public TargetNode findNode(String nodeName) {
        for (TargetNode targetNode : targetNodes) {
            if (targetNode.getName().equals(nodeName)) {
                return targetNode;
            }
        }

        throw new ByggException("No target node name: " + nodeName + " found in DAG");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetDAG targetDAG = (TargetDAG) o;

        return targetNodes.equals(targetDAG.targetNodes);

    }

    @Override
    public int hashCode() {
        return targetNodes != null ? targetNodes.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TargetDAG{" +
                "targetNodes=" + targetNodes +
                '}';
    }

    // TODO: needs lots of tightening up to be really usable.
    public class Builder {
        final String nodeName;
        ByggTargetExecutor executor = null;
        ImmutableSet.Builder<TargetNode> predecessorsBuilder = new ImmutableSet.Builder<TargetNode>();

        public Builder(String nodeName) {
            this.nodeName = Preconditions.checkNotNull(nodeName);
        }

        public Builder executor(ByggTargetExecutor executor) {
            this.executor = Preconditions.checkNotNull(executor);
            return this;
        }

        public Builder predecessors(TargetNode ...nodes) {
            predecessorsBuilder.add(nodes);
            return this;
        }

        public Builder requires(String... nodeNames) {
            for (String nodeName : nodeNames) {
                predecessorsBuilder.add(findNode(nodeName));
            }

            return this;
        }

        public TargetDAG build() {
            TargetNode newNode = new TargetNode(nodeName, executor, predecessorsBuilder.build());

            ImmutableSet.Builder<TargetNode> targetNodeBuilder = new ImmutableSet.Builder<TargetNode>();

            targetNodeBuilder.addAll(targetNodes);
            targetNodeBuilder.add(newNode);

            return new TargetDAG(targetNodeBuilder.build());
        }

        public Builder add(String newNodeName) {
            return build().add(newNodeName);
        }
    }
}
