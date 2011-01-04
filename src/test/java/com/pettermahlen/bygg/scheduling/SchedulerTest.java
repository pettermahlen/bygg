/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.google.common.collect.Sets;
import com.pettermahlen.bygg.BuildResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 27, 2010
 */
public class SchedulerTest {
    private static final List<Schedulable> NO_SCHEDULABLES = Collections.emptyList();
    private static final List<BuildResult> NO_RESULTS = Collections.emptyList();
    private static final List<DagNode<Schedulable>> NO_EDGES = Collections.emptyList();

    Scheduler scheduler;

    SchedulableImpl step1, step2, step3, step4, step5, step6, step7, step8, step9, step10;

    BuildResult result1, result2, result3, result4;

    DagNode<Schedulable> node1a, node2a;

    DagNode<Schedulable> node1b, node2b, node3b, node4b, node5b, node6b, node7b;

    DagNode<Schedulable> node1c, node2c, node3c, node4c, node5c, node6c, node7c, node8c, node9c, node10c;


    @Before
    public void setUp() throws Exception {
        scheduler = new Scheduler();

        result1 = mock(BuildResult.class);
        result2 = mock(BuildResult.class);
        result3 = mock(BuildResult.class);
        result4 = mock(BuildResult.class);

        step1  = new SchedulableImpl(NO_RESULTS, Arrays.asList(result1), NO_SCHEDULABLES, NO_SCHEDULABLES);
        step2  = new SchedulableImpl(NO_RESULTS, Arrays.asList(result2), NO_SCHEDULABLES, NO_SCHEDULABLES);
        step3  = new SchedulableImpl(Arrays.asList(result1), NO_RESULTS, NO_SCHEDULABLES, NO_SCHEDULABLES);
        step4  = new SchedulableImpl(Arrays.asList(result2), Arrays.asList(result3, result4), NO_SCHEDULABLES, NO_SCHEDULABLES);
        step5  = new SchedulableImpl(Arrays.asList(result3), NO_RESULTS, NO_SCHEDULABLES, NO_SCHEDULABLES);
        step6  = new SchedulableImpl(Arrays.asList(result1, result4), NO_RESULTS, NO_SCHEDULABLES, NO_SCHEDULABLES);
        step7  = new SchedulableImpl(NO_RESULTS, NO_RESULTS, NO_SCHEDULABLES, NO_SCHEDULABLES); // DISJOINT
        step8  = new SchedulableImpl(NO_RESULTS, NO_RESULTS, Arrays.asList(step2), NO_SCHEDULABLES);
        step9  = new SchedulableImpl(NO_RESULTS, NO_RESULTS, Arrays.asList(step1, step2), NO_SCHEDULABLES);
        step10 = new SchedulableImpl(NO_RESULTS, NO_RESULTS, Arrays.asList(step6), Arrays.asList(step8));

        node1a = new DagNode<Schedulable>(NO_EDGES, step1);
        node2a = new DagNode<Schedulable>(NO_EDGES, step2);

        node6b = new DagNode<Schedulable>(NO_EDGES, step6);
        node5b = new DagNode<Schedulable>(NO_EDGES, step5);
        node4b = new DagNode<Schedulable>(Arrays.asList(node5b, node6b), step4);
        node3b = new DagNode<Schedulable>(NO_EDGES, step3);
        node1b = new DagNode<Schedulable>(Arrays.asList(node3b, node6b), step1);
        node2b = new DagNode<Schedulable>(Arrays.asList(node4b), step2);
        node7b = new DagNode<Schedulable>(NO_EDGES, step7);

        node8c = new DagNode<Schedulable>(NO_EDGES, step8);
        node9c = new DagNode<Schedulable>(NO_EDGES, step9);
        node10c = new DagNode<Schedulable>(Arrays.asList(node8c), step10);
        node7c = new DagNode<Schedulable>(NO_EDGES, step7);
        node6c = new DagNode<Schedulable>(Arrays.asList(node10c), step6);
        node5c = new DagNode<Schedulable>(NO_EDGES, step5);
        node4c = new DagNode<Schedulable>(Arrays.asList(node5c, node6c), step4);
        node3c = new DagNode<Schedulable>(NO_EDGES, step3);
        node2c = new DagNode<Schedulable>(Arrays.asList(node8c, node4c, node9c), step2);
        node1c = new DagNode<Schedulable>(Arrays.asList(node3c, node6c, node9c), step1);
    }

    @Test
    public void testScheduleNoDependencies() throws Exception {
        assertEquals(Sets.newHashSet(node1a, node2a), scheduler.schedule(Arrays.asList(step1, step2)));
    }

    @Test
    public void testResultDependencies() throws Exception {
        assertEquals(Sets.newHashSet(node1b, node2b, node7b), scheduler.schedule(Arrays.asList(step1, step2, step3, step4, step5, step6, step7)));
    }

    @Test
    public void testPredecessorsSuccessors() throws Exception {
        assertEquals(Sets.newHashSet(node1c, node2c, node7c), scheduler.schedule(Arrays.asList(step1, step2, step3, step4, step5, step6, step7, step8, step9, step10)));
    }

    // TODO: circularity, scheduling impossible, etc.
}
