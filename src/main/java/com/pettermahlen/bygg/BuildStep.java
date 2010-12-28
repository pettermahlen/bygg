/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 14, 2010
 */
public interface BuildStep {
    String getName();

    Set<? extends BuildResult> inputs();
    Set<? extends BuildResult> outputs();

    Set<? extends BuildStep> predecessors();
    Set<? extends BuildStep> successors();

    BuildStepExecutor createExecutor(Injector injector, Reporter reporter);
}
