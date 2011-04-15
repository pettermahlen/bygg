/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
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
public class LocalRepositoryImpl implements LocalRepository {
    private final File repositoryDirectory;

    public LocalRepositoryImpl(File repositoryDirectory) {
        this.repositoryDirectory = Preconditions.checkNotNull(repositoryDirectory, "repositoryDirectory");
        Preconditions.checkArgument(repositoryDirectory.isDirectory(), "%s is not a directory.", repositoryDirectory.getAbsolutePath());
    }

    @Override
    public URL lookup(ArtifactVersion artifactVersion) {
        String expectedPathAndName = pathTo(artifactVersion);

        File file = new File(repositoryDirectory, expectedPathAndName);

        if (!file.isFile()) {
            return null;
        }

        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            // TODO: think about whether this is important/relevant
            throw new RuntimeException(e);
        }
    }

    private String pathTo(ArtifactVersion artifactVersion) {
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


    @Override
    public URL store(ArtifactVersion artifactVersion, final InputStream data) {
        String path = pathTo(artifactVersion);

        File file = new File(repositoryDirectory, path);

        try {
            Files.createParentDirs(file);
            Files.copy(new InputSupplier<InputStream>() {
                @Override
                public InputStream getInput() throws IOException {
                    return data;
                }
            }, file);

            return file.toURI().toURL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
