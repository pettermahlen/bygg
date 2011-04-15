/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.collect.ImmutableList;
import com.pettermahlen.bygg.BuildFailedException;
import com.pettermahlen.bygg.configuration.Artifact;
import com.pettermahlen.bygg.configuration.ArtifactVersion;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class ArtifactDownloaderImplTest {
    ArtifactDownloaderImpl downloader;

    RemoteRepository remoteRepository1;
    RemoteRepository remoteRepository2;

    Artifact artifact;
    RemoteArtifact remoteArtifact;

    InputStream remoteInputStream;
    LocalRepository localRepository;
    URL localUrl;

    @Before
    public void setUp() throws Exception {
        remoteRepository1 = mock(RemoteRepository.class);
        remoteRepository2 = mock(RemoteRepository.class);

        downloader = new ArtifactDownloaderImpl(ImmutableList.of(remoteRepository1, remoteRepository2));

        artifact = new MockArtifact("blah.blopp", "arty");
        remoteArtifact = mock(RemoteArtifact.class);
        remoteInputStream = mock(InputStream.class);

        when(remoteArtifact.open()).thenReturn(remoteInputStream);

        localRepository = mock(LocalRepository.class);
        localUrl = new URL("http://a/local/url?really=true");
    }

    @Test
    public void testDownloadInto() throws Exception {
        final ArtifactVersion artifactVersion = new ArtifactVersion(artifact, "0.9");
        when(remoteRepository1.lookup(eq(artifactVersion))).thenReturn(RemoteArtifact.UNAVAILABLE);
        when(remoteRepository2.lookup(eq(artifactVersion))).thenReturn(remoteArtifact);
        when(localRepository.store(artifactVersion, remoteInputStream)).thenReturn(localUrl);

        assertThat(downloader.downloadInto(artifactVersion, localRepository), equalTo(localUrl));
    }

    @Test
    public void testNotFound() throws Exception {
        final ArtifactVersion artifactVersion = new ArtifactVersion(artifact, "0.9");
        when(remoteRepository1.lookup(eq(artifactVersion))).thenReturn(RemoteArtifact.UNAVAILABLE);
        when(remoteRepository2.lookup(eq(artifactVersion))).thenReturn(RemoteArtifact.UNAVAILABLE);

        try {
            downloader.downloadInto(artifactVersion, localRepository);
            fail("Expected an exception");
        }
        catch (BuildFailedException e) {
            assertThat(e.getMessage(), containsString("Unable to locate artifact"));
            assertThat(e.getMessage(), containsString(artifactVersion.toString()));
            assertThat(e.getMessage(), containsString(remoteRepository1.toString()));
            assertThat(e.getMessage(), containsString(remoteRepository2.toString()));
        }
    }
}
