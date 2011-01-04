/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.google.common.collect.ImmutableSet;
import com.pettermahlen.bygg.BuildResult;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 27, 2010
 */
public class SchedulableImpl implements Schedulable {
    private final ImmutableSet<? extends BuildResult> inputs;
    private final ImmutableSet<? extends BuildResult> outputs;
    private final ImmutableSet<? extends Schedulable> predecessors;
    private final ImmutableSet<? extends Schedulable> successors;

    public SchedulableImpl(Iterable<? extends BuildResult> inputs, Iterable<? extends BuildResult> outputs, Iterable<? extends Schedulable> predecessors, Iterable<? extends Schedulable> successors) {
        this.inputs = ImmutableSet.copyOf(inputs);
        this.outputs = ImmutableSet.copyOf(outputs);
        this.predecessors = ImmutableSet.copyOf(predecessors);
        this.successors = ImmutableSet.copyOf(successors);
    }

    @Override
    public Set<? extends BuildResult> inputs() {
        return inputs;
    }

    @Override
    public Set<? extends BuildResult> outputs() {
        return outputs;
    }

    @Override
    public Set<? extends Schedulable> predecessors() {
        return predecessors;
    }

    @Override
    public Set<? extends Schedulable> successors() {
        return successors;
    }

    @Override
    public String toString() {
        return "SchedulableImpl{" +
                "inputs=" + inputs +
                ", outputs=" + outputs +
                ", predecessors=" + predecessors +
                ", successors=" + successors +
                '}';
    }
}
