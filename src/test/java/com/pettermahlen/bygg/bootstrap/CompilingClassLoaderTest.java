/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class CompilingClassLoaderTest {
    CompilingClassLoader compilingClassLoader;

    @Before
    public void setUp() throws Exception {
        File jarFile = new File("src/test/resources/jar/classinjar.jar");

        URL[] urls = new URL[] { jarFile.toURI().toURL() };


        URLClassLoader parent = new URLClassLoader(urls);

        compilingClassLoader = new CompilingClassLoader(parent, "src/test/resources");
    }

    @Test
    public void testLoadClass() throws Exception {
        Class loaded = compilingClassLoader.loadClass("compilertest.ClassToCompile");

        assertThat(loaded.getName(), equalTo("compilertest.ClassToCompile"));
        assertThat(loaded.getField("CONSTANT").get(null).toString(), equalTo("A constant value"));
    }

    @Test
    public void testLoadClassNotFound() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Test
    public void testLoadClassCompilationError() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }
}
