/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.configuration;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class Configuration implements ByggConfiguration {
    public String vardet() {
        return "värdet";
    }

    public static ByggConfiguration configuration() {
        return new Configuration();
    }
}
