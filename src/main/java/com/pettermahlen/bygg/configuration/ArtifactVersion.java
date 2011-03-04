/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.configuration;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ArtifactVersion {
    private final Artifact artifact;
    private final String version;

    public ArtifactVersion(Artifact artifact, String version) {
        this.artifact = artifact;
        this.version = version;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public String getVersion() {
        return version;
    }
}
