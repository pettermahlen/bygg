/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.Build;
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
    private final HierarchicalClassLoaderSource<URLClassLoader> pluginClassLoaderSource;
    private final HierarchicalClassLoaderSource<ClassLoader> configuredClassLoaderSource;
    private final ByggConfigurationLoader byggConfigurationLoader;

    public ByggBootstrap(ClassLoader parentClassLoader, HierarchicalClassLoaderSource<URLClassLoader> pluginClassLoaderSource, HierarchicalClassLoaderSource<ClassLoader> configuredClassLoaderSource, ByggConfigurationLoader byggConfigurationLoader) {
        this.parentClassLoader = parentClassLoader;
        this.pluginClassLoaderSource = pluginClassLoaderSource;
        this.configuredClassLoaderSource = configuredClassLoaderSource;
        this.byggConfigurationLoader = byggConfigurationLoader;
    }

    public void startBuild(List<String> targetNames, Map<ByggProperty, String> properties) throws Exception {
        // the plugin class loader has access to all plugins needed to compile the configuration and run the build.
        URLClassLoader pluginClassLoader = pluginClassLoaderSource.getClassLoader(parentClassLoader);

        // the configured class loader has access to the actual configuration that is used in the build
        ClassLoader configuredClassLoader = configuredClassLoaderSource.getClassLoader(pluginClassLoader);

        ByggConfiguration byggConfiguration = byggConfigurationLoader.loadConfiguration(configuredClassLoader);
        Build build = byggConfigurationLoader.loadBuild(configuredClassLoader, byggConfiguration, properties);

        build.build(targetNames);
    }
}
