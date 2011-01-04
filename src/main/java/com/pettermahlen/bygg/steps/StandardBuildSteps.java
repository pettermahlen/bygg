/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.pettermahlen.bygg.BuildResult;
import com.pettermahlen.bygg.BuildStep;
import com.pettermahlen.bygg.scheduling.Schedulable;
import com.pettermahlen.bygg.scheduling.SchedulableImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pettermahlen.bygg.steps.StandardBuildResults.*;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public class StandardBuildSteps {
    public static final BuildStepSet BUILDSTEPS = BuildStepSet.builder()
            .step("assembleMainClasspath")
            .outputs(MAIN_CLASSPATH)
            .build()
            .step("assembleMainSources")
            .outputs(MAIN_SOURCES)
            .build()
            .step("compileMain")
            .inputs(MAIN_CLASSPATH, MAIN_SOURCES)
            .outputs(MAIN_CLASSES)
            .build()
            .step("assembleTestClasspath")
            .outputs(TEST_CLASSPATH)
            .build()
            .step("assembleTestSources")
            .outputs(TEST_SOURCES)
            .build()
            .step("compileTest")
            .inputs(TEST_CLASSPATH, TEST_SOURCES)
            .outputs(TEST_CLASSES)
            .build()
            .step("runTests")
            .inputs(MAIN_CLASSES, MAIN_CLASSPATH, TEST_CLASSES, TEST_CLASSPATH)
            .outputs(TEST_RESULT)
            .build()
            .build();

    private static final BuildStepKey<ClasspathConfigurer> ASSEMBLE_MAIN_CLASSPATH = new BuildStepKey<ClasspathConfigurer>("assembleMainClasspath", null);
    private static final BuildStepKey<CompilationConfigurer> COMPILE_MAIN = new BuildStepKey<CompilationConfigurer>("compileMain", null);

    static {
        // TODO: fix 'modify' so that it actually does modification and cannot do otherwise
        //     - this is probably best done inside the Configurer classes
        // TODO: create basic 'build' where each step just prints something; make configuration affect the printouts
        // TODO: fix the build step set builder so that it is clear whether you're building a step or the set
        // TODO: implement a way to deal with global configuration data that isn't specific to a build step. Specifically, the compiler version should be shared between main and test
        // TODO: define the Dependency description API - maybe that's for later?
        BuildStepSet buildStepSet = new BuildStepSet(null);

        // API usage example
        buildStepSet.modify(ASSEMBLE_MAIN_CLASSPATH).output(MAIN_CLASSPATH);
        buildStepSet.modify(COMPILE_MAIN).version("1.5");
        buildStepSet.add(step("runTests")
                .inputs(MAIN_CLASSES, MAIN_CLASSPATH)
                .outputs(TEST_RESULT)
                .build());

    }

    public static BuildStepBuilder step(String name) {
        return new BuildStepBuilder(name);
    }

    private static class BuildStepBuilder {
        private final String name;
        private final List<BuildResult> outputs;
        private final List<BuildResult> inputs;
        private final List<Schedulable> predecessors;
        private final List<Schedulable> successors;

        public BuildStepBuilder(String name) {
            this.name = name;
            outputs = new ArrayList<BuildResult>();
            inputs = new ArrayList<BuildResult>();
            predecessors = new ArrayList<Schedulable>();
            successors = new ArrayList<Schedulable>();
        }


        public BuildStepBuilder outputs(BuildResult... result) {
            outputs.addAll(Arrays.asList(result));
            return this;
        }

        public BuildStepBuilder inputs(BuildResult... result) {
            inputs.addAll(Arrays.asList(result));
            return this;
        }

        public BuildStepBuilder predecessors(Schedulable... result) {
            predecessors.addAll(Arrays.asList(result));
            return this;
        }

        public BuildStepBuilder successors(Schedulable... result) {
            predecessors.addAll(Arrays.asList(result));
            return this;
        }

        public BuildStep build() {
            return new BuildStepImpl(name, new SchedulableImpl(inputs, outputs, predecessors, successors));
        }
    }
}
