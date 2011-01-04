/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.pettermahlen.bygg.*;
import com.pettermahlen.bygg.scheduling.Schedulable;

import java.util.Collection;
import java.util.Set;

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

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends BuildResult> inputs() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends BuildResult> outputs() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends Schedulable> predecessors() {
        throw new UnsupportedOperationException();
    }

    public Set<? extends Schedulable> successors() {
        throw new UnsupportedOperationException();
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

    private static class ClasspathAssembler implements BuildStepExecutor {
        private final Dependencies dependencies;
        private final DependencySelector dependencySelector;
        private final DependencyFinder dependencyFinder;

        @Inject
        public ClasspathAssembler(Dependencies dependencies, DependencySelector dependencySelector, DependencyFinder dependencyFinder) {
            this.dependencies = dependencies;
            this.dependencySelector = dependencySelector;
            this.dependencyFinder = dependencyFinder;
        }

        public Module execute(Reporter reporter) {
            Collection<Dependency> depencies = dependencySelector.select(dependencies);

            final Classpath classpath = dependencyFinder.makeAvailable(depencies);

            return new AbstractModule() {
                @Override
                protected void configure() {
                    bind(dependencySelector.getKey()).toInstance(classpath);
                }
            };
        }
    }
}
