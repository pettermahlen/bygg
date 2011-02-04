/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class Bygg {
    private final ByggConfiguration byggConfiguration;

    public Bygg(ByggConfiguration byggConfiguration) {
        this.byggConfiguration = byggConfiguration;
    }

    public void build() {
        System.out.println("configuration value: " + byggConfiguration.vardet());
    }
}
