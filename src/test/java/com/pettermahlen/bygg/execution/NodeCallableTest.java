/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.pettermahlen.bygg.ByggTargetExecutor;
import com.pettermahlen.bygg.execution.NodeCallable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class NodeCallableTest {
    NodeCallable callable;

    MockExecutor executor;

    ExecutorService executorService;


    @Before
    public void setUp() throws Exception {
        executorService = Executors.newFixedThreadPool(3);

        executor = new MockExecutor();
    }

    @After
    public void tearDown() throws Exception {
        executorService.shutdown();
    }

    @Test
    public void testCallConcurrent() throws Exception {
        Set<Future<?>> futures = new HashSet<Future<?>>();

        final BlockingCallable callable1 = new BlockingCallable();
        final BlockingCallable callable2 = new BlockingCallable();

        futures.add(executorService.submit(callable1));
        futures.add(executorService.submit(callable2));

        callable = new NodeCallable(executor, futures);

        Future<Object> future = executorService.submit(callable);

        // sleeping a very little bit to not slow things down too much; it should fail occasionally if broken
        Thread.sleep(5);

        assertFalse(executor.executed);
        assertFalse(future.isDone());

        callable1.proceed = true;

        Thread.sleep(5);

        assertFalse(executor.executed);
        assertFalse(future.isDone());

        callable2.proceed = true;

        int i = 0;
        while (!future.isDone()) {
            Thread.sleep(3);

            i++;
            if (i == 5) {
                fail("future not done after sleeping for a loong time");
            }
        }

        assertTrue(executor.executed);
    }

    private class BlockingCallable implements Callable<Object> {
        volatile boolean proceed = false;

        @Override
        public Object call() throws Exception {
            while (!proceed) {
                Thread.yield();
            }

            return "done";
        }
    }

    private class MockExecutor implements ByggTargetExecutor {
        private volatile boolean executed = false;

        @Override
        public void execute() {
            executed = true;
        }
    }
}
