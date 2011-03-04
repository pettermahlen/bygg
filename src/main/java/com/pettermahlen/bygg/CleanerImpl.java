/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class CleanerImpl implements Cleaner {
    @Override
    public void clean(String directoryName) {
        File directory = new File(directoryName);

        if (!directory.exists()) {
            // TODO - that's right, I need logging...
            return;
        }

        try {
            Files.deleteRecursively(directory);
        } catch (IOException e) {
            throw new ByggException("failed to delete directory: " + directory.getAbsolutePath(), e);
        }
    }
}
