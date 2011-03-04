/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggArguments {
    private final List<String> targets;
    private final boolean clean;

    public ByggArguments(List<String> targets, boolean clean) {
        this.targets = targets;
        this.clean = clean;
    }

    public List<String> getTargets() {
        return targets;
    }

    public boolean isClean() {
        return clean;
    }
}
