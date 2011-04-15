/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.base.Joiner;
import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class RemoteRepositoryImpl implements RemoteRepository {
    private final URL location;

    public RemoteRepositoryImpl(URL location) {
        this.location = location;
    }

    @Override
    public RemoteArtifact lookup(ArtifactVersion artifactVersion) {
        // let's keep this simple for now; it may be necessary to do something more sophisticated later, but
        // i don't want to decide that yet. This guy will need lots better error checking and stuff, I'm sure.
        try {
            URL remoteArtifactUrl = new URL(location.toExternalForm() + pathFor(artifactVersion));

            InputStream inputStream;

            try {
                inputStream = remoteArtifactUrl.openStream();
            }
            catch (IOException e) {
                // TODO debug log
                return RemoteArtifact.UNAVAILABLE;
            }

            return new RemoteArtifactImpl(inputStream);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String pathFor(ArtifactVersion artifactVersion) {
        List<String> parts = new ArrayList<String>();

        parts.addAll(Arrays.asList(artifactVersion.getArtifact().groupId().split("\\.")));
        parts.add(artifactVersion.getArtifact().artifactId());
        parts.add(artifactVersion.getVersion());
        parts.add(formatArtifactName(artifactVersion));

        return Joiner.on(File.separator).join(parts);
    }


    private String formatArtifactName(ArtifactVersion artifactVersion) {
        return String.format("%s-%s.jar", artifactVersion.getArtifact().artifactId(), artifactVersion.getVersion());
    }
}
