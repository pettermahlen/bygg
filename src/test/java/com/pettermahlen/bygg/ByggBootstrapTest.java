/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggBootstrapTest {
    ByggBootstrap bootstrap;

    ClassLoader parentClassLoader;
    HierarchicalClassLoaderSource pluginClassLoaderSource;
    HierarchicalClassLoaderSource configuredClassLoaderSource;
    ByggConfigurationLoader byggConfigurationLoader;

    ClassLoader pluginClassLoader;
    ClassLoader configurationAndBuildClassLoader;

    ByggConfiguration byggConfiguration;
    Build build;

    List<String> targetNames;
    Map<ByggProperty, String> properties;

    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {
        parentClassLoader = mock(ClassLoader.class);
        pluginClassLoaderSource = mock(HierarchicalClassLoaderSource.class);
        configuredClassLoaderSource = mock(HierarchicalClassLoaderSource.class);
        byggConfigurationLoader = mock(ByggConfigurationLoader.class);

        bootstrap = new ByggBootstrap(parentClassLoader, pluginClassLoaderSource, configuredClassLoaderSource, byggConfigurationLoader);

        byggConfiguration = mock(ByggConfiguration.class);
        build = mock(Build.class);

        targetNames = mock(List.class);
        properties = mock(Map.class);
    }

    @Test
    public void testStartBuild() throws Exception {
        when(pluginClassLoaderSource.getClassLoader(parentClassLoader)).thenReturn(pluginClassLoader);
        when(configuredClassLoaderSource.getClassLoader(pluginClassLoader)).thenReturn(configurationAndBuildClassLoader);
        when(byggConfigurationLoader.loadConfiguration(configurationAndBuildClassLoader)).thenReturn(byggConfiguration);
        when(byggConfigurationLoader.loadBuild(configurationAndBuildClassLoader, byggConfiguration, properties)).thenReturn(build);

        bootstrap.startBuild(targetNames, properties);

        verify(build).build(targetNames);
    }
}
