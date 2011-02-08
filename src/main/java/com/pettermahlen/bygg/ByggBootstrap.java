/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class ByggBootstrap {
    private final ClassLoader parentClassLoader;
    private final ConfiguredClassLoaderSource configuredClassLoaderSource;

    public ByggBootstrap(ClassLoader parentClassLoader, ConfiguredClassLoaderSource configuredClassLoaderSource) {
        this.parentClassLoader = parentClassLoader;
        this.configuredClassLoaderSource = configuredClassLoaderSource;
    }

    // TODO: add build targets 
    public void startBuild(String[] targetNames, Map<ByggProperty, String> properties) throws Exception {
        // bootstrap build:
        // - get a bootstrap class loader for figuring out the configuration
        // - get a class loader that has access to the configuration for the rest of the build
        // - get the configuration instance out of the configured class loader
        // - get a build executor instance out of the configured class loader
        // - kick off the build using the executor instance and the configuration instance

        // TODO: actually need another class loader here that can find plugins needed to build the build.

        // TODO: should possibly use some property to enable tweaking the location of the configuration sources
        ClassLoader configuredClassLoader = configuredClassLoaderSource.getClassLoader(parentClassLoader);

        ByggConfiguration byggConfiguration = getByggConfigurationUsingLoader(configuredClassLoader);

        Build build = getBuildInstance(configuredClassLoader, byggConfiguration, properties);

        build.build(targetNames);
    }

    // TODO: probably factor this out into something that isn't so tied to class loader management. Basically, at this level, we probably just want to get the config somehow?
    private ByggConfiguration getByggConfigurationUsingLoader(ClassLoader configuredClassLoader) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        // TODO: should possibly make the configuration class name configurable, although that might be taking things a step too far?
        Class byggConfigurationClass = configuredClassLoader.loadClass("Configuration");

        Method configurationMethod = byggConfigurationClass.getMethod("configuration", null);

        return (ByggConfiguration) configurationMethod.invoke(null, null);
    }

    private Build getBuildInstance(ClassLoader configuredClassLoader, ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<Build> buildClass = (Class<Build>) configuredClassLoader.loadClass("com.pettermahlen.bygg.Build");

        return buildClass.getConstructor(ByggConfiguration.class, Map.class).newInstance(byggConfiguration, properties);
    }


    public static void main(String[] args) throws Exception {
        ConfiguredClassLoaderSource janinoClassLoaderSource = new JaninoClassLoaderSource("src/test/bygg");

        URL[] urls = new URL[] { new URL("file:/Users/pettermahlen/.m2/repository/bygg-test-plugin/bygg-test-plugin/1.0-SNAPSHOT/bygg-test-plugin-1.0-SNAPSHOT.jar") };
        ClassLoader parentClassLoader = new URLClassLoader(urls, ByggBootstrap.class.getClassLoader());

        ByggBootstrap bootstrap = new ByggBootstrap(parentClassLoader, janinoClassLoaderSource);

        bootstrap.startBuild(new String[0], Collections.<ByggProperty, String>emptyMap());
    }
}
