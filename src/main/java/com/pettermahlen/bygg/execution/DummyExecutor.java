/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.google.common.io.Files;
import com.pettermahlen.bygg.ByggTargetExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class DummyExecutor implements ByggTargetExecutor {
    private final String name;

    public DummyExecutor(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        File file = new File("target/" + name);

        final String message = name + " executed at: " + new Date().toString() + "\n";

        try {
            Files.createParentDirs(file);
            Files.append(message, file, Charset.forName("UTF-8"));

            System.out.println(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
