/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.pettermahlen.bygg.ByggTargetExecutor;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class NodeCallableFactoryImpl implements NodeCallableFactory {
    @Override
    public Callable<?> createCallable(ByggTargetExecutor executor, Set<Future<?>> futures) {
        return new NodeCallable(executor, futures);
    }
}
