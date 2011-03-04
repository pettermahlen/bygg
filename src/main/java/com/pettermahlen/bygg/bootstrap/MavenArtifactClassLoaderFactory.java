/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public interface MavenArtifactClassLoaderFactory {
    ClassLoader classLoaderFor(ClassLoader parent, List<ArtifactVersion> artifactVersions);
}
