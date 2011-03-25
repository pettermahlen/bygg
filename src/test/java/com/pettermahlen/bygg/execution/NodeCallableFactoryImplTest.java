/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.pettermahlen.bygg.ByggTargetExecutor;
import com.pettermahlen.bygg.execution.NodeCallable;
import com.pettermahlen.bygg.execution.NodeCallableFactoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class NodeCallableFactoryImplTest {
    NodeCallableFactoryImpl nodeCallableFactory;

    ByggTargetExecutor executor;
    Set<Future<?>> futures;

    @Before
    public void setUp() throws Exception {
        nodeCallableFactory = new NodeCallableFactoryImpl();

        executor = mock(ByggTargetExecutor.class);
        futures = new HashSet<Future<?>>();
    }

    @Test
    public void testCreateCallable() throws Exception {
        assertThat(nodeCallableFactory.createCallable(executor, futures), equalTo((Callable) new NodeCallable(executor, futures)));
    }
}
