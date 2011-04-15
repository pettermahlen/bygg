/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.collect.ImmutableList;
import com.pettermahlen.bygg.ByggTargetExecutor;
import com.pettermahlen.bygg.configuration.ArtifactVersion;

import java.net.URL;
import java.util.Collection;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class ClasspathAssembler implements ByggTargetExecutor {
    // go through list of required dependencies, download if necessary,
    // return a list of URLs to the jar files (for now)

    private final LocalRepository localRepository;
    private final ArtifactDownloader artifactDownloader;

    public ClasspathAssembler(LocalRepository localRepository, ArtifactDownloader artifactDownloader) {
        this.localRepository = localRepository;
        this.artifactDownloader = artifactDownloader;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException();
    }

    // TODO: needs error checking/the ability to throw a build failure exception.
    // TODO: should maybe use sets instead of iterables/collections. we only want single instances of everything
    public Collection<URL> executeReal(Iterable<? extends ArtifactVersion> requiredArtifacts) {
        ImmutableList.Builder<URL> resultBuilder = ImmutableList.builder();

        for (ArtifactVersion artifactVersion : requiredArtifacts) {
            URL artifactUrl = localRepository.lookup(artifactVersion);

            if (artifactUrl == null) {
                artifactUrl = artifactDownloader.downloadInto(artifactVersion, localRepository);
            }

            resultBuilder.add(artifactUrl);
        }

        return resultBuilder.build();
     }
}
