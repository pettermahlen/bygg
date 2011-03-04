/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import java.io.File;

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

        boolean result = directory.delete();

        if (!result) {
            throw new ByggException("failed to delete directory: " + directory.getAbsolutePath());
        }
    }
}
