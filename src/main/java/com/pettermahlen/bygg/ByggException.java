/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggException extends RuntimeException {
    public ByggException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ByggException(String message) {
        super(message);
    }
}
