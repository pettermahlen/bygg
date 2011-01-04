/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.pettermahlen.bygg.BuildResult;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public interface Schedulable {
    Set<? extends BuildResult> inputs();

    Set<? extends BuildResult> outputs();

    Set<? extends Schedulable> predecessors();

    Set<? extends Schedulable> successors();
}
