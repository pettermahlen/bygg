/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 14, 2010
 */
public class Bygg {
    public void build(Buildline buildline, Reporter reporter) {

        // APIs for this guy:
        // - configuring the build (which build steps to take, in what order, what the build steps mean (exact classes implementing them, configurations, etc))
        //    => done using a Buildline, which has a list of BuildSteps. Need to have nice default build lines and nice APIs for creating custom ones.
        // - configuring dependencies (using Maven-style dependencies, or using Bygg-style dependencies)
        //    => done somehow. Probably as a configuration parameter of the classPathAssemblers (there should be three or so: compiling, packaging and testing)
        //    'scope' of dependencies is probably a set of bit flags: include into PRODUCT, TEST, PACKAGE, and should be transitively applied.
        // - running/reporting
        //    => running is easy, reporting is done by explicit messages to a Reporter implementation
        // - we also need some sort of build scope that gets populated with relevant data - we may need to re-inject/re-bind some things between each build step
        //   to ensure that we can do all the necessary injection. Can this be done by Guice?!

        Injector injector = Guice.createInjector();

        // TODO: in the future, we should make a scheduler that figures out dependencies between build steps and parallelizes them.

        for (BuildStep buildStep : buildline.getBuildSteps()) {
            BuildStepExecutor buildStepExecutor = buildStep.createExecutor(injector, reporter);

            Module module = buildStepExecutor.execute(reporter);

            injector = injector.createChildInjector(module);
        }
    }
}
