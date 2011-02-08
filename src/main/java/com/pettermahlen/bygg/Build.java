/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class Build {
    private final ByggConfiguration byggConfiguration;
    private final Map<ByggProperty, String> properties;

    public Build(ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties) {
        this.byggConfiguration = byggConfiguration;
        this.properties = properties;
    }

    public void build(String[] targetNames) {
        System.out.println("configuration value: " + byggConfiguration.vardet());
    }
}
