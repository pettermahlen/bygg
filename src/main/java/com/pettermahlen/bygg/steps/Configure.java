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
import com.pettermahlen.bygg.executors.BuildConfigurator;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class Configure implements BuildStep {
    public String getName() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends BuildResult> inputs() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends BuildResult> outputs() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends BuildStep> predecessors() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends BuildStep> successors() {
        throw new UnsupportedOperationException();
    }

    public BuildStepExecutor createExecutor(Injector injector, Reporter reporter) {
        // TODO: make this configurable, so you can actually tweak the configuration of the build.
        return new BuildConfigurator();
    }
}
