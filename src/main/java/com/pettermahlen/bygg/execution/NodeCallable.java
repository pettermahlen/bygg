/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.pettermahlen.bygg.ByggException;
import com.pettermahlen.bygg.ByggTargetExecutor;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class NodeCallable implements Callable<Object> {
    private final ByggTargetExecutor executor;
    private final ImmutableSet<Future<?>> futures;

    public NodeCallable(ByggTargetExecutor executor, Set<Future<?>> futures) {
        this.executor = Preconditions.checkNotNull(executor, "executor");
        this.futures = ImmutableSet.copyOf(futures);
    }

    @Override
    public Object call() throws Exception {
        try {
            for (Future<?> future : futures) {
                future.get();
            }

            executor.execute();

            return "done";
        }
        catch (ExecutionException e) {
            throw new ByggException("Failed to run executor: " + executor, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeCallable that = (NodeCallable) o;

        return executor.equals(that.executor) && futures.equals(that.futures);

    }

    @Override
    public int hashCode() {
        int result = executor.hashCode();
        result = 31 * result + futures.hashCode();
        return result;
    }
}
