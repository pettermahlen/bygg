/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.google.common.base.Joiner;
import com.pettermahlen.bygg.configuration.Artifact;
import com.pettermahlen.bygg.configuration.ArtifactVersion;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class DependencyClassLoaderFactoryImplTest {
    DependencyClassLoaderFactoryImpl factory;

    ClassLoader parent;
    List<ArtifactVersion> artifactVersions;

    URL[] expectedUrls;

    @Before
    public void setUp() throws Exception {
        factory = new DependencyClassLoaderFactoryImpl();

        parent = mock(ClassLoader.class);

        artifactVersions = Arrays.asList(new ArtifactVersion(TestArtifact.ONE, "1.0"), new ArtifactVersion(TestArtifact.TWO, "1.1-SNAPSHOT"));

        expectedUrls = new URL[2];

        String homeDirectory = System.getProperty("user.home");

        expectedUrls[0] = createUrl(System.getProperty("file.separator"), homeDirectory, "com.shopzilla", "one", "1.0");
        expectedUrls[1] = createUrl(System.getProperty("file.separator"), homeDirectory, "com.pettermahlen.bygg", "bygg-test", "1.1-SNAPSHOT");
    }

    @Test
    public void testClassLoaderFor() throws Exception {
        // TODO: might want to consider having the option of using different .m2 repositories in the future
        ClassLoader actual = factory.classLoaderFor(parent, artifactVersions);

        assertThat(actual, instanceOf(URLClassLoader.class));

        URL[] urls = ((URLClassLoader) actual).getURLs();

        assertThat(urls, equalTo(expectedUrls));
    }

    enum TestArtifact implements Artifact {
        ONE("com.shopzilla", "one"),
        TWO("com.pettermahlen.bygg", "bygg-test")
        ;
        final String groupId;
        final String artifactId;

        TestArtifact(String groupId, String artifactId) {
            this.groupId = groupId;
            this.artifactId = artifactId;
        }


        public String groupId() {
            return groupId;
        }

        public String artifactId() {
            return artifactId;
        }
    }

    private URL createUrl(String fileSeparator, String homeDirectory, String groupId, String artifactId, String version) throws MalformedURLException {
        String[] groupParts = groupId.split("\\.");

        Joiner pathJoiner = Joiner.on(fileSeparator);

        List<String> stringParts = new ArrayList<String>();

        stringParts.add("file:" + homeDirectory);
        stringParts.add(".m2");
        stringParts.add("repository");
        stringParts.addAll(Arrays.asList(groupParts));
        stringParts.add(artifactId);
        stringParts.add(version);
        stringParts.add(String.format("%s-%s.jar", artifactId, version));

        String url = pathJoiner.join(stringParts);

        return new URL(url);
    }

}
