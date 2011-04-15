/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.pettermahlen.bygg.configuration.ArtifactVersion;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class LocalRepositoryImplTest {
    LocalRepositoryImpl repository;

    ArtifactVersion  artifactVersion;
    File repositoryDirectory;

    @Before
    public void setUp() throws Exception {
        repositoryDirectory = new File("target/localrepository");
        repositoryDirectory.mkdirs();
        
        repository = new LocalRepositoryImpl(repositoryDirectory);

        File artifact = new File(repositoryDirectory, "com/bygg/bygg-test-artifact/1.2/bygg-test-artifact-1.2.jar");

        Files.createParentDirs(artifact);
        Files.append("lite data", artifact, Charset.forName("UTF-8"));
    }

    @Test
    public void testNullRepositoryDirectory() throws Exception {
        try {
            new LocalRepositoryImpl(null);
            fail("expected an exception");
        }
        catch (NullPointerException e) {
            assertThat(e.getMessage(), containsString("repositoryDirectory"));
        }
    }

    @Test
    public void testRepositoryDirectoryNotADirectory() throws Exception {
        try {
            new LocalRepositoryImpl(new File("thisfiledoesn'texist"));
            fail("expected an exception");
        }
        catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("is not a directory"));
            assertThat(e.getMessage(), containsString("thisfiledoesn'texist"));
        }
    }

    @Test
    public void testLookupExists() throws Exception {
        artifactVersion = new ArtifactVersion(ARTIFACT, "1.2");

        // TODO: this test is probably broken on windows, so fix it
        assertThat(repository.lookup(artifactVersion), equalTo(new URL("file:"+ repositoryDirectory.getAbsolutePath() + "/com/bygg/bygg-test-artifact/1.2/bygg-test-artifact-1.2.jar")));
    }

    @Test
    public void testLookupNotFound() throws Exception {
        artifactVersion = new ArtifactVersion(ARTIFACT, "1.2-SNAPSHOT");

        assertThat(repository.lookup(artifactVersion), CoreMatchers.<Object>nullValue());
    }

    @Test
    public void testStore() throws Exception {
        InputStream data = ByteStreams.newInputStreamSupplier("hejsan vad bra".getBytes()).getInput();

        String path = Joiner.on(File.separatorChar).join("com", "bygg", "bygg-test-artifact", "2.3", "bygg-test-artifact-2.3.jar");

        File expectedFile = new File(repositoryDirectory, path);

        expectedFile.delete();
        assertFalse(expectedFile.exists());

        URL actualURl = repository.store(new ArtifactVersion(ARTIFACT, "2.3"), data);

        assertThat(actualURl, equalTo(new URL("file:" + repositoryDirectory.getAbsolutePath() + "/com/bygg/bygg-test-artifact/2.3/bygg-test-artifact-2.3.jar")));

        assertTrue(expectedFile.exists());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ByteStreams.copy(new FileInputStream(expectedFile), outputStream);

        assertThat(outputStream.toString(), equalTo("hejsan vad bra"));
    }

    private static final MockArtifact ARTIFACT = new MockArtifact("com.bygg", "bygg-test-artifact");

}

