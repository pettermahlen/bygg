/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.Build;
import com.pettermahlen.bygg.NodeCallableFactory;
import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;
import com.pettermahlen.bygg.execution.TargetDAG;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggConfigurationLoaderImplTest {
    ByggConfigurationLoaderImpl loader;

    ExecutorService executorService;
    NodeCallableFactory nodeCallableFactory;

    ClassLoader classLoader;

    Map<ByggProperty, String> properties;

    static final ByggConfiguration byggConfiguration = new ByggConfiguration() {
        @Override
        public TargetDAG getTargetDAG() {
            throw new UnsupportedOperationException();
        }
    };



    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {

        classLoader = mock(ClassLoader.class);
        executorService = mock(ExecutorService.class);
        nodeCallableFactory = mock(NodeCallableFactory.class);

        loader = new ByggConfigurationLoaderImpl(executorService, nodeCallableFactory);

        properties = mock(Map.class);
    }

    @Test
    public void testLoadConfiguration() throws Exception {
        // this suppression+casting is necessary due to mockito weirdness..
        //noinspection unchecked
        when(classLoader.loadClass("Configuration")).thenReturn((Class) ConfigurationClass.class);

        assertThat(byggConfiguration, equalTo(loader.loadConfiguration(classLoader)));
    }

    @Test
    public void testLoadBuild() throws Exception {
        //noinspection unchecked
        when(classLoader.loadClass("com.pettermahlen.bygg.Build")).thenReturn((Class) BuildClass.class);

        Object build = loader.loadBuild(classLoader, byggConfiguration, properties);

        assertThat(build, instanceOf(BuildClass.class));
    }

    private static class ConfigurationClass {
        public static ByggConfiguration configuration() {
            return byggConfiguration;
        }
    }

    static class BuildClass extends Build {
        public BuildClass(ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties, ExecutorService executorService, NodeCallableFactory nodeCallableFactory) {
            super(byggConfiguration, properties, executorService, nodeCallableFactory);
        }
    }

}
