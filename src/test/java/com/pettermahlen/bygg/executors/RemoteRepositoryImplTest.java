/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.pettermahlen.bygg.configuration.Artifact;
import com.pettermahlen.bygg.configuration.ArtifactVersion;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class RemoteRepositoryImplTest {
    RemoteRepositoryImpl remoteRepository;

    File repositoryDirectory;

    ArtifactVersion available;
    ArtifactVersion unavailable;
    String artifactData;

    @Before
    public void setUp() throws Exception {
        repositoryDirectory = new File("target/remoterepository");
        repositoryDirectory.mkdirs();

        available = new ArtifactVersion(AVAILABLE_ARTIFACT, "1.0");
        unavailable = new ArtifactVersion(UNAVAILABLE_ARTIFACT, "1.0");

        remoteRepository = new RemoteRepositoryImpl(repositoryDirectory.toURI().toURL());

        File availableFile = new File(repositoryDirectory, "grupp/artefakt/1.0/artefakt-1.0.jar");


        Files.createParentDirs(availableFile);
        artifactData = "the data that goes into the artifact";
        Files.write(artifactData, availableFile, Charset.forName("UTF-8"));
    }

    @Test
    public void testLookup() throws Exception {
        RemoteArtifact actual = remoteRepository.lookup(available);

        InputStream actualData = actual.open();

        assertThat(ByteStreams.toByteArray(actualData), equalTo(artifactData.getBytes()));
    }

    @Test
    public void testLookupFail() throws Exception {
        final RemoteArtifact lookup = remoteRepository.lookup(unavailable);
        assertThat(lookup, equalTo(RemoteArtifact.UNAVAILABLE));
    }

    private static final Artifact AVAILABLE_ARTIFACT = new MockArtifact("grupp", "artefakt");
    private static final Artifact UNAVAILABLE_ARTIFACT = new MockArtifact("grupp", "annan-artefakt");
}
