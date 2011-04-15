/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import java.io.InputStream;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public interface RemoteArtifact {
    static final RemoteArtifact UNAVAILABLE = new RemoteArtifact() {
        @Override
        public InputStream open() {
            throw new UnsupportedOperationException();
        }
    };

    InputStream open();
}
