/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.google.common.base.Joiner;
import com.pettermahlen.bygg.ByggException;
import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 29/03/2011
 */
public class MavenArtifactLocator {
    static URL createUrl(ArtifactVersion artifactVersion) {
        String homeDirectory = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");

        List<String> parts = new ArrayList<String>();

        parts.add("file:" + homeDirectory);
        parts.add(".m2");
        parts.add("repository");
        parts.addAll(Arrays.asList(artifactVersion.getArtifact().groupId().split("\\.")));
        parts.add(artifactVersion.getArtifact().artifactId());
        parts.add(artifactVersion.getVersion());
        parts.add(formatArtifactName(artifactVersion));

        String url = Joiner.on(fileSeparator).join(parts);

        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new ByggException("Unable to create well-formed URL for artifact version: " + artifactVersion, e);
        }
    }

    static String formatArtifactName(ArtifactVersion artifactVersion) {
        return String.format("%s-%s.jar", artifactVersion.getArtifact().artifactId(), artifactVersion.getVersion());
    }
}
