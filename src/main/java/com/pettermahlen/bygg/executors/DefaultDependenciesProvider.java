/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.inject.Provider;
import com.pettermahlen.bygg.Dependencies;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class DefaultDependenciesProvider implements Provider<Dependencies> {
    public Dependencies get() {
        // TODO: figure out a way to get hold of the dependencies implementation for this build configuration 
        throw new UnsupportedOperationException();
    }
}
