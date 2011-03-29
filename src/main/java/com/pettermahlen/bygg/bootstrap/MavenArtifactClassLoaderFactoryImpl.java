/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class MavenArtifactClassLoaderFactoryImpl implements MavenArtifactClassLoaderFactory {
    @Override
    public URLClassLoader classLoaderFor(ClassLoader parent, List<ArtifactVersion> artifactVersions) {
        List<URL> urls = new ArrayList<URL>(artifactVersions.size());

        for (ArtifactVersion artifactVersion : artifactVersions) {
            urls.add(MavenArtifactLocator.createUrl(artifactVersion));
        }

        return new URLClassLoader(urls.toArray(new URL[urls.size()]), parent);
    }

}
