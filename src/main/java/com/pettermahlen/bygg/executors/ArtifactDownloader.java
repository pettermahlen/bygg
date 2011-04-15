/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.net.URL;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public interface ArtifactDownloader {
    URL downloadInto(ArtifactVersion artifactVersion, LocalRepository localRepository);
}
