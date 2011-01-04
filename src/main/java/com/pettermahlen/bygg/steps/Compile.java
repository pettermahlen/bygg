/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.google.inject.Injector;
import com.pettermahlen.bygg.*;
import com.pettermahlen.bygg.dependency.ClasspathType;
import com.pettermahlen.bygg.scheduling.Schedulable;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class Compile implements BuildStep {
    public Compile(ClasspathType classpathType) {
        //To change body of created methods use File | Settings | File Templates.
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
        throw new UnsupportedOperationException();
    }


    public static class Builder {
        
    }
}
