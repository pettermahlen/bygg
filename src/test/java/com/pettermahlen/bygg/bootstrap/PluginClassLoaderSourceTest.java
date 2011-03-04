/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.configuration.ArtifactVersion;
import com.pettermahlen.bygg.configuration.Plugins;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class PluginClassLoaderSourceTest {
    PluginClassLoaderSource classLoaderSource;

    Loader<Plugins> pluginsLoader;
    DependencyClassLoaderFactory dependencyClassLoaderFactory;

    ClassLoader parentClassLoader;
    Plugins plugins;
    List<ArtifactVersion> artifactVersions;
    ClassLoader dependencyAwareClassLoader;

    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {
        pluginsLoader = mock(Loader.class);
        dependencyClassLoaderFactory = mock(DependencyClassLoaderFactory.class);

        classLoaderSource = new PluginClassLoaderSource(pluginsLoader, dependencyClassLoaderFactory);

        parentClassLoader = mock(ClassLoader.class);
        plugins = mock(Plugins.class);
        artifactVersions = mock(List.class);
        dependencyAwareClassLoader = mock(ClassLoader.class);
    }

    @Test
    public void testGetClassLoader() throws Exception {
        when(pluginsLoader.load(parentClassLoader)).thenReturn(plugins);
        when(plugins.plugins()).thenReturn(artifactVersions);
        when(dependencyClassLoaderFactory.classLoaderFor(parentClassLoader, artifactVersions)).thenReturn(dependencyAwareClassLoader);

        ClassLoader actual = classLoaderSource.getClassLoader(parentClassLoader);

        assertThat(actual, equalTo(dependencyAwareClassLoader));
    }
}
