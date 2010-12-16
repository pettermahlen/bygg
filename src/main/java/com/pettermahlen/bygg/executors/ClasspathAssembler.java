/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.pettermahlen.bygg.*;

import java.util.Collection;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 14, 2010
 */
public class ClasspathAssembler implements BuildStepExecutor {
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
                bind(dependencySelector.getKey())
                        .toInstance(classpath);

            }
        };
    }
}
