/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.dependency.ClasspathType;
import com.pettermahlen.bygg.dependency.DependencySelectorImpl;
import com.pettermahlen.bygg.dependency.DependencyFinderImpl;
import com.pettermahlen.bygg.steps.*;

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

    public static Buildline libraryJar() {
        return new Buildline() {
            public List<BuildStep> getBuildSteps() {
                return Arrays.asList(
                        new Configure(),
                        new AssembleClasspath(new DependencySelectorImpl(ClasspathType.PRODUCT), new DependencyFinderImpl()),
                        new AssembleSources(new SourceSelectorImpl(ClasspathType.PRODUCT)),
                        new Compile(ClasspathType.PRODUCT),
                        new AssembleClasspath(new DependencySelectorImpl(ClasspathType.TEST), new DependencyFinderImpl()),
                        new AssembleSources(new SourceSelectorImpl(ClasspathType.TEST)),
                        new Compile(ClasspathType.TEST),
                        new RunTest(),
                        new PackageArtifact()
                );
            }
        };
    }
}
