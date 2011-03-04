/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class ByggBootstrap {
    private final ClassLoader parentClassLoader;
    private final HierarchicalClassLoaderSource pluginClassLoaderSource;
    private final HierarchicalClassLoaderSource configuredClassLoaderSource;
    private final ByggConfigurationLoader byggConfigurationLoader;

    public ByggBootstrap(ClassLoader parentClassLoader, HierarchicalClassLoaderSource pluginClassLoaderSource, HierarchicalClassLoaderSource configuredClassLoaderSource, ByggConfigurationLoader byggConfigurationLoader) {
        this.parentClassLoader = parentClassLoader;
        this.pluginClassLoaderSource = pluginClassLoaderSource;
        this.configuredClassLoaderSource = configuredClassLoaderSource;
        this.byggConfigurationLoader = byggConfigurationLoader;
    }

    public void startBuild(List<String> targetNames, Map<ByggProperty, String> properties) throws Exception {
        // bootstrap build:
        // - get a bootstrap class loader for figuring out the configuration
        // - get a class loader that has access to the configuration for the rest of the build
        // - get the configuration instance out of the configured class loader
        // - get a build executor instance out of the configured class loader
        // - kick off the build using the executor instance and the configuration instance

        // the plugin class loader has access to all plugins needed to compile the configuration and run the build.
        ClassLoader pluginClassLoader = pluginClassLoaderSource.getClassLoader(parentClassLoader);

        // the configured class loader has access to the actual configuration that is used in the build
        ClassLoader configuredClassLoader = configuredClassLoaderSource.getClassLoader(pluginClassLoader);

        ByggConfiguration byggConfiguration = byggConfigurationLoader.loadConfiguration(configuredClassLoader);
        Build build = byggConfigurationLoader.loadBuild(configuredClassLoader, byggConfiguration, properties);

        build.build(targetNames);
    }


    public static void main(String[] args) throws Exception {
        HierarchicalClassLoaderSource janinoClassLoaderSource = new JaninoClassLoaderSource("src/test/bygg");

        URL[] urls = new URL[] { new URL("file:/Users/pettermahlen/.m2/repository/bygg-test-plugin/bygg-test-plugin/1.0-SNAPSHOT/bygg-test-plugin-1.0-SNAPSHOT.jar") };
        ClassLoader parentClassLoader = new URLClassLoader(urls, ByggBootstrap.class.getClassLoader());

        ByggBootstrap bootstrap = new ByggBootstrap(parentClassLoader, null, janinoClassLoaderSource, null);

        bootstrap.startBuild(Collections.<String>emptyList(), Collections.<ByggProperty, String>emptyMap());
    }
}
