/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.configuration.Plugins;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class PluginClassLoaderSource implements HierarchicalClassLoaderSource {
    private final Loader<Plugins> pluginsLoader;
    private final DependencyClassLoaderFactory dependencyClassLoaderFactory;

    public PluginClassLoaderSource(Loader<Plugins> pluginsLoader, DependencyClassLoaderFactory dependencyClassLoaderFactory) {
        this.pluginsLoader = pluginsLoader;
        this.dependencyClassLoaderFactory = dependencyClassLoaderFactory;
    }


    public ClassLoader getClassLoader(ClassLoader parent) {
        // compile and load the plugin definitions
        // for each plugin, add a URL to some list
        // return a URL class loader that has access to all plugins
        Plugins plugins = pluginsLoader.load(parent);

        return dependencyClassLoaderFactory.classLoaderFor(parent, plugins.plugins());
    }
}
