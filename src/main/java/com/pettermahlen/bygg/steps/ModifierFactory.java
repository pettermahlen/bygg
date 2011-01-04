/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.pettermahlen.bygg.BuildStep;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public interface ModifierFactory<T> {

    T create(BuildStepSet buildStepSet, String stepName);
}
