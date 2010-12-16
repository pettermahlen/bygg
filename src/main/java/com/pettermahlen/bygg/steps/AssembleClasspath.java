/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.pettermahlen.bygg.*;
import com.pettermahlen.bygg.executors.ClasspathAssembler;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class AssembleClasspath implements BuildStep {
    private final DependencySelector dependencySelector;
    private final DependencyFinder dependencyFinder;

    @Inject
    public AssembleClasspath(DependencySelector dependencySelector, DependencyFinder dependencyFinder) {
        this.dependencySelector = dependencySelector;
        this.dependencyFinder = dependencyFinder;
    }

    public BuildStepExecutor createExecutor(Injector injector, Reporter reporter) {
        Injector executorInjector = injector.createChildInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DependencySelector.class).toInstance(dependencySelector);
                bind(DependencyFinder.class).toInstance(dependencyFinder);
            }
        });

        return executorInjector.getInstance(ClasspathAssembler.class);
    }
}
