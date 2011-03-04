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
    private final HierarchicalClassLoaderSource compilingClassLoaderSource;
    private final MavenArtifactClassLoaderFactory mavenArtifactClassLoaderFactory;

    public PluginClassLoaderSource(Loader<Plugins> pluginsLoader, HierarchicalClassLoaderSource compilingClassLoaderSource, MavenArtifactClassLoaderFactory mavenArtifactClassLoaderFactory) {
        this.pluginsLoader = pluginsLoader;
        this.compilingClassLoaderSource = compilingClassLoaderSource;
        this.mavenArtifactClassLoaderFactory = mavenArtifactClassLoaderFactory;
    }


    public ClassLoader getClassLoader(ClassLoader parent) {
        ClassLoader compilingClassLoader = compilingClassLoaderSource.getClassLoader(parent);

        Plugins plugins = pluginsLoader.load(compilingClassLoader);

        return mavenArtifactClassLoaderFactory.classLoaderFor(parent, plugins.plugins());
    }
}
