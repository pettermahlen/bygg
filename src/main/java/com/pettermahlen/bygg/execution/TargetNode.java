/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.pettermahlen.bygg.ByggTargetExecutor;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class TargetNode {
    private final String name;
    private final ByggTargetExecutor executor;
    private final Set<TargetNode> predecessors;

    public TargetNode(String name, ByggTargetExecutor executor, Iterable<TargetNode> predecessors) {
        this.name = Preconditions.checkNotNull(name, "name");
        this.executor = Preconditions.checkNotNull(executor, "executor");
        this.predecessors = ImmutableSet.copyOf(predecessors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetNode that = (TargetNode) o;

        return executor.equals(that.executor) && name.equals(that.name) && predecessors.equals(that.predecessors);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + executor.hashCode();
        result = 31 * result + predecessors.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TargetNode{" +
                "name='" + name + '\'' +
                ", executor=" + executor +
                ", requires=" + predecessors +
                '}';
    }

    public String getName() {
        return name;
    }

    public Set<TargetNode> getPredecessors() {
        return predecessors;
    }

    public ByggTargetExecutor getExecutor() {
        return executor;
    }
}
