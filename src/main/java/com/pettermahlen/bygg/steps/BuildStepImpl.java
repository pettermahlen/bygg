/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.google.inject.Injector;
import com.pettermahlen.bygg.BuildResult;
import com.pettermahlen.bygg.BuildStep;
import com.pettermahlen.bygg.BuildStepExecutor;
import com.pettermahlen.bygg.Reporter;
import com.pettermahlen.bygg.scheduling.Schedulable;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public class BuildStepImpl implements BuildStep {
    private final String name;
    private final Schedulable schedulable;

    public BuildStepImpl(String name, Schedulable schedulable) {
        this.name = name;
        this.schedulable = schedulable;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public BuildStepExecutor createExecutor(Injector injector, Reporter reporter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<? extends BuildResult> inputs() {
        return schedulable.inputs();
    }

    @Override
    public Set<? extends BuildResult> outputs() {
        return schedulable.outputs();
    }

    @Override
    public Set<? extends Schedulable> predecessors() {
        return schedulable.predecessors();
    }

    @Override
    public Set<? extends Schedulable> successors() {
        return schedulable.successors();
    }
}
