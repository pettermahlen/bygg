/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;
import com.pettermahlen.bygg.execution.TargetDAG;
import com.pettermahlen.bygg.execution.TargetNode;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class BuildTest {
    Build build;

    ByggConfiguration byggConfiguration;
    Map<ByggProperty, String> properties;
    ExecutorService executorService;
    NodeCallableFactory nodeCallableFactory;

    TargetDAG targetDag;
    ByggTargetExecutor assembleMainExecutor;
    ByggTargetExecutor compileExecutor;
    ByggTargetExecutor assembleTestExecutor;
    ByggTargetExecutor compileTestExecutor;
    ByggTargetExecutor resourcesExecutor;
    ByggTargetExecutor testExecutor;
    ByggTargetExecutor packageExecutor;
    ByggTargetExecutor javadocExecutor;
    ByggTargetExecutor sourcesExecutor;
    ByggTargetExecutor installExecutor;

    Callable<?> assembleMainCallable;
    Callable<?> compileCallable;
    Callable<?> assembleTestCallable;
    Callable<?> compileTestCallable;
    Callable<?> resourcesCallable;
    Callable<?> testCallable;
    Callable<?> packageCallable;
    Callable<?> javadocCallable;
    Callable<?> sourcesCallable;
    Callable<?> installCallable;

    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {
        byggConfiguration = mock(ByggConfiguration.class);
        properties = ImmutableMap.of();
        executorService = mock(ExecutorService.class);
        nodeCallableFactory = mock(NodeCallableFactory.class);

        build = new Build(byggConfiguration, properties, executorService, nodeCallableFactory);

        assembleMainCallable = mock(Callable.class);
        compileCallable = mock(Callable.class);
        assembleTestCallable = mock(Callable.class);
        compileTestCallable = mock(Callable.class);
        resourcesCallable = mock(Callable.class);
        testCallable = mock(Callable.class);
        packageCallable = mock(Callable.class);
        javadocCallable = mock(Callable.class);
        sourcesCallable = mock(Callable.class);
        installCallable = mock(Callable.class);

        assembleMainExecutor = mock(ByggTargetExecutor.class);
        compileExecutor = mock(ByggTargetExecutor.class);
        assembleTestExecutor = mock(ByggTargetExecutor.class);
        compileTestExecutor = mock(ByggTargetExecutor.class);
        resourcesExecutor = mock(ByggTargetExecutor.class);
        testExecutor = mock(ByggTargetExecutor.class);
        packageExecutor = mock(ByggTargetExecutor.class);
        javadocExecutor = mock(ByggTargetExecutor.class);
        sourcesExecutor = mock(ByggTargetExecutor.class);
        installExecutor = mock(ByggTargetExecutor.class);

        targetDag = createDag();
        when(byggConfiguration.getTargetDAG()).thenReturn(targetDag);

        when(nodeCallableFactory.createCallable(same(assembleMainExecutor), anySet())).thenReturn((Callable) assembleMainCallable);
        when(nodeCallableFactory.createCallable(same(compileExecutor), anySet())).thenReturn((Callable) compileCallable);
        when(nodeCallableFactory.createCallable(same(assembleTestExecutor), anySet())).thenReturn((Callable) assembleTestCallable);
        when(nodeCallableFactory.createCallable(same(compileTestExecutor), anySet())).thenReturn((Callable) compileTestCallable);
        when(nodeCallableFactory.createCallable(same(resourcesExecutor), anySet())).thenReturn((Callable) resourcesCallable);
        when(nodeCallableFactory.createCallable(same(testExecutor), anySet())).thenReturn((Callable) testCallable);
        when(nodeCallableFactory.createCallable(same(packageExecutor), anySet())).thenReturn((Callable) packageCallable);
        when(nodeCallableFactory.createCallable(same(javadocExecutor), anySet())).thenReturn((Callable) javadocCallable);
        when(nodeCallableFactory.createCallable(same(sourcesExecutor), anySet())).thenReturn((Callable) sourcesCallable);
        when(nodeCallableFactory.createCallable(same(installExecutor), anySet())).thenReturn((Callable) installCallable);
    }

    @Test
    public void testBuildSingleTargetNoRequirements() throws Exception {

        build.build(ImmutableList.of("assembleMain"));

        verify(executorService).submit(assembleMainCallable);
        verify(executorService).shutdown();
        verifyNoMoreInteractions(executorService);
    }

    @Test
    public void testMultipleTargetsNoSharedPaths() throws Exception {
        build.build(ImmutableList.of("compileTest", "resources"));

        verify(executorService).submit(assembleMainCallable);
        verify(executorService).submit(compileCallable);
        verify(executorService).submit(assembleTestCallable);
        verify(executorService).submit(compileTestCallable);
        verify(executorService).submit(resourcesCallable);
        verify(executorService).shutdown();
        verifyNoMoreInteractions(executorService);
    }

    @Test
    public void testWholeGraphRedundantTargets() throws Exception {
        build.build(ImmutableList.of("compileTest", "install"));

        verify(executorService).submit(assembleMainCallable);
        verify(executorService).submit(compileCallable);
        verify(executorService).submit(assembleTestCallable);
        verify(executorService).submit(compileTestCallable);
        verify(executorService).submit(resourcesCallable);
        verify(executorService).submit(testCallable);
        verify(executorService).submit(packageCallable);
        verify(executorService).submit(javadocCallable);
        verify(executorService).submit(sourcesCallable);
        verify(executorService).submit(installCallable);
        verify(executorService).shutdown();
        verifyNoMoreInteractions(executorService);
    }

    private TargetDAG createDag() {
        return new TargetDAG(ImmutableList.<TargetNode>of())
                .add("assembleMain").executor(assembleMainExecutor)
                .add("compile").executor(compileExecutor).requires("assembleMain")
                .add("assembleTest").executor(assembleTestExecutor)
                .add("compileTest").executor(compileTestExecutor).requires("compile", "assembleTest")
                .add("resources").executor(resourcesExecutor)
                .add("test").executor(testExecutor).requires("compileTest", "resources")
                .add("package").executor(packageExecutor).requires("test")
                .add("javadoc").executor(javadocExecutor).requires("assembleMain")
                .add("sources").executor(sourcesExecutor).requires("assembleMain")
                .add("install").executor(installExecutor).requires("package", "javadoc", "sources")
                .build();
    }

}
