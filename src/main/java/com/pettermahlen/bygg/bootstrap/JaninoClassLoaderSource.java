/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import org.codehaus.janino.DebuggingInformation;
import org.codehaus.janino.JavaSourceClassLoader;

import java.io.File;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class JaninoClassLoaderSource implements HierarchicalClassLoaderSource {
    private  final String sourcePath;

    public JaninoClassLoaderSource(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public ClassLoader getClassLoader(ClassLoader parent) {
        return new JavaSourceClassLoader(parent, new File[] { new File(sourcePath)}, null, DebuggingInformation.ALL);
    }
}
