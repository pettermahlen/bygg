/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.inject.Key;

import java.util.Collection;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public interface DependencySelector {
    Key<Classpath> getKey();

    Collection<Dependency> select(Dependencies dependencies);
}
