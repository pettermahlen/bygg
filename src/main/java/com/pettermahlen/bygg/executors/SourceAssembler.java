/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.pettermahlen.bygg.BuildStepExecutor;
import com.pettermahlen.bygg.Reporter;
import com.pettermahlen.bygg.SourceSelector;
import com.pettermahlen.bygg.Sources;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 14, 2010
 */
public class SourceAssembler implements BuildStepExecutor {
    private final SourceSelector sourceSelector;

    @Inject
    public SourceAssembler(SourceSelector sourceSelector) {
        this.sourceSelector = sourceSelector;
    }


    public Module execute(Reporter reporter) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(Sources.class).toInstance(null);
            }
        };
    }
}
