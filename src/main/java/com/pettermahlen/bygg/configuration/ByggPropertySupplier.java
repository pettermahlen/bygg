/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.configuration;

import com.google.common.base.Supplier;
import com.google.inject.internal.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 07/02/2011
 */
public class ByggPropertySupplier implements Supplier<Map<ByggProperty, String>> {
    public Map<ByggProperty, String> get() {
        // TODO: make this guy check for property definitions that override the defaults in: a global,
        // checked-in file, a local, not version-controlled file and the cmd-line params. Maybe the params should be added as well so
        // that this guy can locate global properties somewhere else than in the default location. If that is such a good idea... I don't know.

        ImmutableMap.Builder<ByggProperty, String> mapBuilder = new ImmutableMap.Builder<ByggProperty, String>();

        for (ByggProperty byggProperty : ByggProperty.values()) {
            mapBuilder.put(byggProperty, byggProperty.getDefaultValue());
        }

        return mapBuilder.build();
    }
}
