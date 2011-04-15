/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.collect.ImmutableList;
import com.pettermahlen.bygg.BuildFailedException;
import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class ArtifactDownloaderImpl implements ArtifactDownloader {
    private final List<RemoteRepository> remoteRepositories;

    public ArtifactDownloaderImpl(Iterable<? extends RemoteRepository> remoteRepositories) {
        this.remoteRepositories = ImmutableList.copyOf(remoteRepositories);
    }

    @Override
    public URL downloadInto(ArtifactVersion artifactVersion, LocalRepository localRepository) {
        for (RemoteRepository remoteRepository : remoteRepositories) {
            RemoteArtifact remoteArtifact = remoteRepository.lookup(artifactVersion);

            if (remoteArtifact != RemoteArtifact.UNAVAILABLE) {
                return localRepository.store(artifactVersion, remoteArtifact.open());
            }
        }

        throw new BuildFailedException("Unable to locate artifact version: " + artifactVersion + " in repositories: " + remoteRepositories);
    }
}
