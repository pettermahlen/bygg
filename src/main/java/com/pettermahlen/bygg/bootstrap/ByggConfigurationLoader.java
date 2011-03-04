/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.Build;
import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public interface ByggConfigurationLoader {
    ByggConfiguration loadConfiguration(ClassLoader classLoader);
    Build loadBuild(ClassLoader classLoader, ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties);
}
