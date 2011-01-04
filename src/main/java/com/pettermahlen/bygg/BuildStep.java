/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.inject.Injector;
import com.pettermahlen.bygg.scheduling.Schedulable;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 14, 2010
 */
public interface BuildStep extends Schedulable {
    String getName();

    BuildStepExecutor createExecutor(Injector injector, Reporter reporter);
}
