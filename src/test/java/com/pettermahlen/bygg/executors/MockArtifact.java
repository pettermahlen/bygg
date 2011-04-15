/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.pettermahlen.bygg.configuration.Artifact;

/**
*
* @author Petter Måhlén
* @since 15/04/2011
*/
class MockArtifact implements Artifact {
    private final String groupId;
    private final String artifactId;

    public MockArtifact(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }


    @Override
    public String groupId() {
        return groupId;
    }

    @Override
    public String artifactId() {
        return artifactId;
    }
}
