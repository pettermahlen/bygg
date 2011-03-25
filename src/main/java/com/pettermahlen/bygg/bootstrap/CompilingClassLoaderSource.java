/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import java.net.URLClassLoader;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class CompilingClassLoaderSource implements HierarchicalClassLoaderSource {
    private final String sourcePath;

    public CompilingClassLoaderSource(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public ClassLoader getClassLoader(ClassLoader parent) {
        return new CompilingClassLoader((URLClassLoader) parent, sourcePath);
    }
}
