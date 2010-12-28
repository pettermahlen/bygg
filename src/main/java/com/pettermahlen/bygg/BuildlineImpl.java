/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 16, 2010
 */
public class BuildlineImpl implements Buildline {
    private final List<BuildStep> buildSteps;

    @Inject
    public BuildlineImpl(List<BuildStep> buildSteps) {
        this.buildSteps = ImmutableList.copyOf(buildSteps);
    }

    public List<BuildStep> getBuildSteps() {
        throw new UnsupportedOperationException();
    }
}
