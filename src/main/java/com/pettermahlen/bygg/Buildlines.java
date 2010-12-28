/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.inject.*;
import com.pettermahlen.bygg.dependency.ClasspathType;
import com.pettermahlen.bygg.dependency.DependencySelectorImpl;
import com.pettermahlen.bygg.dependency.DependencyFinderImpl;
import com.pettermahlen.bygg.steps.*;

import java.util.Arrays;
import java.util.List;

import static com.google.inject.name.Names.named;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class Buildlines {
    private Injector injector = Guice.createInjector(
            new AbstractModule() {
                @Override
                protected void configure() {
                    // TODO: shared, public stuff?
                }
            },
            new PrivateModule() {

                @Override
                protected void configure() {
                    bind(Buildline.class).annotatedWith(named("library-jar")).to(BuildlineImpl.class);
                    expose(Buildline.class).annotatedWith(named("library-jar"));

                    bind(new TypeLiteral<List<BuildStep>>() {}).toProvider(new LibraryJarBuildStepProvider());
                }
            }

    );

    public Buildlines() {
    }

    public Buildline libraryJar() {
        return injector.getInstance(Key.get(Buildline.class, named("library-jar")));

//        return new Buildline() {
//            public List<BuildStep> getBuildSteps() {
//                return Arrays.asList(
//                        new Configure(),
//                        new AssembleClasspath(new DependencySelectorImpl(ClasspathType.PRODUCT), new DependencyFinderImpl()),
//                        new AssembleSources(new SourceSelectorImpl(ClasspathType.PRODUCT)),
//                        new Compile(ClasspathType.PRODUCT),
//                        new AssembleClasspath(new DependencySelectorImpl(ClasspathType.TEST), new DependencyFinderImpl()),
//                        new AssembleSources(new SourceSelectorImpl(ClasspathType.TEST)),
//                        new Compile(ClasspathType.TEST),
//                        new RunTest(),
//                        new PackageArtifact()
//                );
//            }
//        };
    }



    private class LibraryJarBuildStepProvider implements Provider<List<BuildStep>> {
        public List<BuildStep> get() {
            throw new UnsupportedOperationException();
        }
    }
}
