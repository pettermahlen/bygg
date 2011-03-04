/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.Build;
import com.pettermahlen.bygg.bootstrap.ByggConfigurationLoaderImpl;
import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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

    ClassLoader classLoader;

    Map<ByggProperty, String> properties;

    static final ByggConfiguration byggConfiguration = new ByggConfiguration() {
        public String vardet() {
            throw new UnsupportedOperationException();
        }
    };



    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {
        loader = new ByggConfigurationLoaderImpl();

        classLoader = mock(ClassLoader.class);

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
        public BuildClass(ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties) {
            super(byggConfiguration, properties);
        }
    }

}
