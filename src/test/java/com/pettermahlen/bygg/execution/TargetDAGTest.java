/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import com.google.common.collect.ImmutableSet;
import com.pettermahlen.bygg.ByggTargetExecutor;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class TargetDAGTest {
    TargetDAG dag1, dag1a, dag2;

    ByggTargetExecutor executor;
    Set<TargetNode> nodes1;

    TargetNode node1;
    TargetNode node2;
    TargetNode node3;

    @Before
    public void setUp() throws Exception {
        executor = mock(ByggTargetExecutor.class);

        node1 = new TargetNode("hej", executor, Collections.<TargetNode>emptySet());
        node2 = new TargetNode("hopp", executor, Collections.<TargetNode>emptySet());
        node3 = new TargetNode("hipp", executor, Arrays.asList(node2));

        nodes1 = ImmutableSet.of(node1);
        Set<TargetNode> nodes2 = Collections.emptySet();


        dag1 = new TargetDAG(nodes1);
        dag1a = new TargetDAG(nodes1);
        dag2 = new TargetDAG(nodes2);
    }

    @Test
    public void testAdd() throws Exception {
        TargetDAG expectedDag = new TargetDAG(ImmutableSet.of(node1, node3));
        TargetDAG actualDag = dag1.add("hipp").executor(executor).predecessors(node2).build();

        assertThat(actualDag, equalTo(expectedDag));
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(dag1.equals(dag1));
        assertTrue(dag1.equals(dag1a));
        assertFalse(dag1.equals(dag2));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(dag1.hashCode(), dag1.hashCode());
        assertEquals(dag1.hashCode(), dag1a.hashCode());
        assertFalse(dag1.hashCode() == dag2.hashCode());
    }
}
