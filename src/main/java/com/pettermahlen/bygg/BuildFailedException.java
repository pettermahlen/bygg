/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 15/04/2011
 */
public class BuildFailedException extends ByggException {
    public BuildFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BuildFailedException(String message) {
        super(message);
    }
}
