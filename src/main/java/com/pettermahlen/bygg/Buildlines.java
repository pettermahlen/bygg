/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.dependency.CompileDependencySelector;
import com.pettermahlen.bygg.dependency.DependencyFinderImpl;
import com.pettermahlen.bygg.steps.BuildConfiguration;
import com.pettermahlen.bygg.steps.ClasspathAssembly;

import java.util.Arrays;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class Buildlines {
    private Buildlines() {
        
    }

    public static Buildline jarLibrary() {
        return new Buildline() {
            public List<BuildStep> getBuildSteps() {
                return Arrays.asList(
                        new BuildConfiguration(),
                        new ClasspathAssembly(new CompileDependencySelector(), new DependencyFinderImpl())
                        );
            }
        };
    }
}
