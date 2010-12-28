/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import com.pettermahlen.bygg.BuildResult;
import com.pettermahlen.bygg.BuildStep;
import com.pettermahlen.bygg.BuildStepExecutor;
import com.pettermahlen.bygg.Reporter;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 27, 2010
 */
public class SchedulableBuildStep implements BuildStep {
    private final String name;
    private final ImmutableSet<? extends BuildResult> inputs;
    private final ImmutableSet<? extends BuildResult> outputs;
    private final ImmutableSet<? extends BuildStep> predecessors;
    private final ImmutableSet<? extends BuildStep> successors;

    public SchedulableBuildStep(String name, Iterable<? extends BuildResult> inputs, Iterable<? extends BuildResult> outputs, Iterable<? extends BuildStep> predecessors, Iterable<? extends BuildStep> successors) {
        this.name = name;
        this.inputs = ImmutableSet.copyOf(inputs);
        this.outputs = ImmutableSet.copyOf(outputs);
        this.predecessors = ImmutableSet.copyOf(predecessors);
        this.successors = ImmutableSet.copyOf(successors);
    }

    public String getName() {
        return name;
    }

    public Set<? extends BuildResult> inputs() {
        return inputs;
    }

    public Set<? extends BuildResult> outputs() {
        return outputs;
    }

    public Set<? extends BuildStep> predecessors() {
        return predecessors;
    }

    public Set<? extends BuildStep> successors() {
        return successors;
    }

    public BuildStepExecutor createExecutor(Injector injector, Reporter reporter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "SchedulableBuildStep{" +
                "name='" + name + '\'' +
                '}';
    }
}
