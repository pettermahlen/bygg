/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class CleanerImplTest {
    CleanerImpl cleaner;

    File directoryToClean;

    @Before
    public void setUp() throws Exception {
        cleaner = new CleanerImpl();

        directoryToClean = new File("target/test-clean");

        directoryToClean.mkdirs();

        File file = new File(directoryToClean, "hej");
        file.createNewFile();
    }

    @Test
    public void testClean() throws Exception {
        assertTrue(directoryToClean.isDirectory());

        cleaner.clean("target/test-clean");

        assertFalse(directoryToClean.exists());
    }

    @Test
    public void testCleanNonExistentDirectory() throws Exception {
        cleaner.clean("thisdirectoryShouldn'tbehere");
    }
}
