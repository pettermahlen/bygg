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
    MavenArtifactClassLoaderFactory mavenArtifactClassLoaderFactory;
    HierarchicalClassLoaderSource compilingClassLoaderSource;

    ClassLoader parentClassLoader;
    ClassLoader compilingClassLoader;
    Plugins plugins;
    List<ArtifactVersion> artifactVersions;
    ClassLoader dependencyAwareClassLoader;

    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {
        pluginsLoader = mock(Loader.class);
        mavenArtifactClassLoaderFactory = mock(MavenArtifactClassLoaderFactory.class);
        compilingClassLoaderSource = mock(HierarchicalClassLoaderSource.class);

        classLoaderSource = new PluginClassLoaderSource(pluginsLoader, compilingClassLoaderSource, mavenArtifactClassLoaderFactory);

        parentClassLoader = mock(ClassLoader.class);
        compilingClassLoader = mock(ClassLoader.class);
        plugins = mock(Plugins.class);
        artifactVersions = mock(List.class);
        dependencyAwareClassLoader = mock(ClassLoader.class);
    }

    @Test
    public void testGetClassLoader() throws Exception {
        when(compilingClassLoaderSource.getClassLoader(parentClassLoader)).thenReturn(compilingClassLoader);
        when(pluginsLoader.load(compilingClassLoader)).thenReturn(plugins);
        when(plugins.plugins()).thenReturn(artifactVersions);
        when(mavenArtifactClassLoaderFactory.classLoaderFor(parentClassLoader, artifactVersions)).thenReturn(dependencyAwareClassLoader);

        ClassLoader actual = classLoaderSource.getClassLoader(parentClassLoader);

        assertThat(actual, equalTo(dependencyAwareClassLoader));
    }
}
