/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableSet;
import com.pettermahlen.bygg.BuildResult;
import com.pettermahlen.bygg.BuildStep;
import com.pettermahlen.bygg.scheduling.Schedulable;
import com.pettermahlen.bygg.scheduling.SchedulableImpl;
import sun.rmi.rmic.newrmic.Main;

import java.util.*;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public class BuildStepSet extends ForwardingSet<BuildStep> implements Set<BuildStep> {
    private final Set<BuildStep> delegate;

    public BuildStepSet(Set<BuildStep> delegate) {
        this.delegate = ImmutableSet.copyOf(delegate);
    }

    @Override
    public boolean add(BuildStep element) {
        // TODO: check name uniqueness
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends BuildStep> buildSteps) {
        return standardAddAll(buildSteps);
    }

    @Override
    protected Set<BuildStep> delegate() {
        return delegate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T modify(BuildStepKey<T> key) {
        return key.getModifierFactory().create(this, key.getName());
    }

    public BuildStep findStep(String name) {
        for (BuildStep step : delegate) {
            if (step.getName().equals(name)) {
                return step;
            }
        }

        throw new IllegalArgumentException("No step with name: " + name + " present in build step set");
    }

    // TODO: this should probably be package private
    public void replace(String name, BuildStep replacement) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public static class Builder {
        private Set<BuildStep> buildSteps = new HashSet<BuildStep>();

        public StepBuilder step(String name) {
            return new StepBuilder(this, name);
        }

        public BuildStepSet build() {
            return new BuildStepSet(buildSteps);
        }
    }
    
    public static class StepBuilder {
        private final Builder setBuilder;
        private final String name;
        private final List<BuildResult> outputs;
        private final List<BuildResult> inputs;
        private final List<Schedulable> predecessors;
        private final List<Schedulable> successors;

        public StepBuilder(Builder setBuilder, String name) {
            this.setBuilder = setBuilder;
            this.name = name;
            outputs = new ArrayList<BuildResult>();
            inputs = new ArrayList<BuildResult>();
            predecessors = new ArrayList<Schedulable>();
            successors = new ArrayList<Schedulable>();
        }


        public StepBuilder outputs(BuildResult... result) {
            outputs.addAll(Arrays.asList(result));
            return this;
        }

        public StepBuilder inputs(BuildResult... result) {
            inputs.addAll(Arrays.asList(result));
            return this;
        }

        public StepBuilder predecessors(Schedulable... result) {
            predecessors.addAll(Arrays.asList(result));
            return this;
        }

        public StepBuilder successors(Schedulable... result) {
            predecessors.addAll(Arrays.asList(result));
            return this;
        }

        public Builder build() {
            final BuildStepImpl buildStep = new BuildStepImpl(name, new SchedulableImpl(inputs, outputs, predecessors, successors));

            setBuilder.buildSteps.add(buildStep);

            return setBuilder;
        }
    }
}
