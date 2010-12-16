/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.google.inject.Injector;
import com.pettermahlen.bygg.*;
import com.pettermahlen.bygg.executors.SourceAssembler;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class AssembleSources implements BuildStep {
    private final SourceSelector sourceSelector;

    public AssembleSources(SourceSelector sourceSelector) {
        this.sourceSelector = sourceSelector;
    }

    public BuildStepExecutor createExecutor(Injector injector, Reporter reporter) {
        return injector.getInstance(SourceAssembler.class);
        
    }
}
