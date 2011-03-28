/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;


import org.codehaus.commons.compiler.jdk.JavaSourceClassLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class JaninoClassLoaderSource implements HierarchicalClassLoaderSource<ClassLoader> {
    private  final String sourcePath;

    public JaninoClassLoaderSource(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public ClassLoader getClassLoader(ClassLoader parent) {
        // TODO: hack that needs to be fixed

        if (!(parent instanceof URLClassLoader)) {
            throw new IllegalArgumentException("parent must be a URLClassLoader");
        }

        URLClassLoader urlClassLoader = (URLClassLoader) parent;

        JavaSourceClassLoader result = new JavaSourceClassLoader(parent);

        result.setSourcePath(new File[] { new File(sourcePath) });
        result.setCompilerOptions(classPathOptionFor(urlClassLoader.getURLs()));


        return result;
    }

    private String[] classPathOptionFor(URL[] urls) {
        String[] result = new String[2];

        result[0] = "-classpath";
        result[1] = classpathForUrls(urls);

        return result;
    }

    // TODO: this is broken and needs to be fixed. It needs to be the full classpath for everything that is needed
    // for Bygg as well as the current set of plugins.
    private String classpathForUrls(URL[] urls) {
        StringBuilder builder = new StringBuilder();

        for (URL url : urls) {
            builder.append(url.getFile()).append(File.pathSeparator);
        }

        return builder.toString();
    }
}
