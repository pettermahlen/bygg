/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class RemoteArtifactImpl implements RemoteArtifact {
    private final InputStream inputStream;

    public RemoteArtifactImpl(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream open() {
        return inputStream;
    }
}
