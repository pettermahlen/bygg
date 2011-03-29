/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;


import com.google.common.collect.ImmutableList;
import com.pettermahlen.bygg.configuration.ArtifactVersion;
import org.codehaus.commons.compiler.jdk.JavaSourceClassLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class JaninoClassLoaderSource implements HierarchicalClassLoaderSource<ClassLoader> {
    private final String sourcePath;

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

    private String[] classPathOptionFor(URL[] pluginUrls) {
        URL[] coreUrls = createCoreUrls();

        String[] result = new String[2];

        result[0] = "-classpath";
        result[1] = classpathForUrls(coreUrls, pluginUrls);

        return result;
    }

    private URL[] createCoreUrls() {
        ArrayList<URL> urls = new ArrayList<URL>(CoreByggArtifacts.VERSIONS.size());

        for (ArtifactVersion artifactVersion : CoreByggArtifacts.VERSIONS) {
            urls.add(MavenArtifactLocator.createUrl(artifactVersion));
        }

        return urls.toArray(new URL[urls.size()]);
    }

    private String classpathForUrls(URL[] coreUrls, URL[] pluginUrls) {
        StringBuilder builder = new StringBuilder();

        addUrls(coreUrls, builder);
        addUrls(pluginUrls, builder);

        return builder.toString();
    }

    private void addUrls(URL[] urls, StringBuilder builder) {
        for (URL url : urls) {
            builder.append(url.getFile()).append(File.pathSeparator);
        }
    }

}
