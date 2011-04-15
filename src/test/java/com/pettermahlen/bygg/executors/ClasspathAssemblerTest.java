/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.common.collect.ImmutableList;
import com.pettermahlen.bygg.configuration.Artifact;
import com.pettermahlen.bygg.configuration.ArtifactVersion;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class ClasspathAssemblerTest {
    ClasspathAssembler assembler;

    LocalRepository localRepository;
    ArtifactDownloader artifactDownloader;

    List<ArtifactVersion> dependencies;

    URL oneUrl;
    URL twoUrl;

    @Before
    public void setUp() throws Exception {
        localRepository = mock(LocalRepository.class);
        artifactDownloader = mock(ArtifactDownloader.class);

        assembler = new ClasspathAssembler(localRepository, artifactDownloader);

        oneUrl = new URL("http://localhost/one");
        twoUrl = new URL("http://localhost/two");
    }

    @Test
    public void testExecuteReal() throws Exception {
        final ArtifactVersion oneVersion = new ArtifactVersion(MyArtifacts.ONE, "1.0");
        final ArtifactVersion twoVersion = new ArtifactVersion(MyArtifacts.TWO, "1.5");

        dependencies = ImmutableList.of(oneVersion, twoVersion);

        when(localRepository.lookup(oneVersion)).thenReturn(null);
        when(localRepository.lookup(twoVersion)).thenReturn(twoUrl);
        when(artifactDownloader.downloadInto(oneVersion, localRepository)).thenReturn(oneUrl);

        Collection<URL> actual = assembler.executeReal(dependencies);

        assertThat(actual.size(), equalTo(2));
        assertTrue(actual.contains(oneUrl));
        assertTrue(actual.contains(twoUrl));
    }


    private static enum MyArtifacts implements Artifact {
        ONE("one.group", "one-artifact"),
        TWO("two.groups", "the-second")
        ;

        private final String groupId;
        private final String artifactId;

        MyArtifacts(String groupId, String artifactId) {
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
}
