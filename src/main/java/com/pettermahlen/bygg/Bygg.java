/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.base.Supplier;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 07/02/2011
 */
public class Bygg {
    private final Supplier<Map<ByggProperty, String>> propertiesSupplier;
    private final Cleaner cleaner;
    private final ByggBootstrap byggBootstrap;

    public Bygg(Supplier<Map<ByggProperty, String>> propertiesSupplier, Cleaner cleaner, ByggBootstrap byggBootstrap) {
        this.propertiesSupplier = propertiesSupplier;
        this.cleaner = cleaner;
        this.byggBootstrap = byggBootstrap;
    }

    public void build(boolean cleanRequired, String[] targetNames) throws Exception {
        // read properties
        // do clean if specified
        // kick off regular build if specified
        Map<ByggProperty, String> properties = propertiesSupplier.get();

        if (cleanRequired) {
            cleaner.clean(properties.get(ByggProperty.TARGET_DIR));
        }

        if (targetNames.length > 0) {
            byggBootstrap.startBuild(targetNames, properties);
        }
    }

    public static void main(String[] args) {
        // parse cmd-line args
        // initialise a Bygg instance
        // kick off
    }
}
