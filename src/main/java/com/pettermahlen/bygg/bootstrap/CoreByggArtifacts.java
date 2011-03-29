/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.google.common.collect.ImmutableList;
import com.pettermahlen.bygg.configuration.Artifact;
import com.pettermahlen.bygg.configuration.ArtifactVersion;

/**
 * Enumerates core dependencies that are needed to build Bygg itself. This is needed in order to
 * do compilation of configurations in projects.
 *
 * @author Petter Måhlén
 * @since 29/03/2011
 */
public enum CoreByggArtifacts implements Artifact {
    GUAVA("com.google.guava", "guava"),
    JANINO_COMPILER_JDK("org.codehaus.janino", "commons-compiler-jdk"),
    JANINO_COMPILER("org.codehaus.janino", "commons-compiler"),
    JOPT_SIMPLE("net.sf.jopt-simple", "jopt-simple"),
    BYGG("com.pettermahlen.bygg", "bygg")
    ;

    private final String groupId;
    private final String artifactId;

    CoreByggArtifacts(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public String groupId() {
        return groupId;
    }

    public String artifactId() {
        return artifactId;
    }

    static final ImmutableList<ArtifactVersion> VERSIONS = ImmutableList.of(
            new ArtifactVersion(GUAVA, "r08"),
            new ArtifactVersion(JANINO_COMPILER, "2.6.1"),
            new ArtifactVersion(JANINO_COMPILER_JDK, "2.6.1"),
            new ArtifactVersion(JOPT_SIMPLE, "3.2"),
            new ArtifactVersion(BYGG, "1.0-SNAPSHOT")
    );
}
