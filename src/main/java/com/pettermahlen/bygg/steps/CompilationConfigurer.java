/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.pettermahlen.bygg.BuildStep;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Jan 4, 2011
 */
public class CompilationConfigurer implements BuildStepConfigurer {
    private final BuildStepSet buildStepSet;
    private final String name;

    public CompilationConfigurer(BuildStepSet buildStepSet, String name) {
        this.buildStepSet = buildStepSet;
        this.name = name;
    }

    public CompilationConfigurer version(String sourceVersion) {
        BuildStep previous = buildStepSet.findStep(name);

        // TODO: work out how to deal with typesafe build steps...
        if (!(previous instanceof Compile)) {
            throw new IllegalStateException("Build step with name: " + name + " isn't a Compile step");
        }

        Compile previousCompile = (Compile) previous;

        // TOOD: create a new Compile instance with the updated version information and use that below.
        buildStepSet.replace(name, previousCompile);

        return this;
    }
}
