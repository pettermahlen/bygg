/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import org.junit.Before;
import org.junit.Test;

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
public class MethodInvokingLoaderTest {
    MethodInvokingLoader<String> loader;

    ClassLoader classLoader;

    @Before
    public void setUp() throws Exception {
        loader = new MethodInvokingLoader<String>("ByggPlugins", "getPlugins");

        classLoader = mock(ClassLoader.class);
    }

    @Test
    public void testLoad() throws Exception {
        // this suppression+casting is necessary due to mockito weirdness..
        //noinspection unchecked
        when(classLoader.loadClass("ByggPlugins")).thenReturn((Class) PluginsClass.class);

        assertThat("these are the plugins - sorta", equalTo(loader.load(classLoader)));
    }

    private static class PluginsClass {
        public static String getPlugins() {
            return "these are the plugins - sorta";
        }
    }
}
