/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.pettermahlen.bygg.BuildStepExecutor;
import com.pettermahlen.bygg.Dependencies;
import com.pettermahlen.bygg.Reporter;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class BuildConfigurator implements BuildStepExecutor {
    private DefaultDependenciesProvider dependenciesProvider = new DefaultDependenciesProvider();


    public Module execute(Reporter reporter) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(Dependencies.class).toProvider(dependenciesProvider);
                
            }
        };
    }
}
