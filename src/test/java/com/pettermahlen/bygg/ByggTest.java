/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.base.Supplier;
import com.pettermahlen.bygg.bootstrap.ByggBootstrap;
import com.pettermahlen.bygg.configuration.ByggProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggTest {
    Bygg bygg;

    Supplier<Map<ByggProperty, String>> propertiesSupplier;
    Cleaner cleaner;
    ByggBootstrap bootstrap;

    Map<ByggProperty, String> properties;
    List<String> targetNames;

    @SuppressWarnings({"unchecked"})
    @Before
    public void setUp() throws Exception {
        propertiesSupplier = mock(Supplier.class);
        cleaner = mock(Cleaner.class);
        bootstrap = mock(ByggBootstrap.class);

        bygg = new Bygg(propertiesSupplier, cleaner, bootstrap);

        properties = new HashMap<ByggProperty, String>();

        when(propertiesSupplier.get()).thenReturn(properties);
        targetNames = Arrays.asList("dummy target 1", "dummy target two");
    }

    @Test
    public void testBuildNoClean() throws Exception {
        bygg.build(false, targetNames);

        verify(cleaner, never()).clean(anyString());
        verify(bootstrap).startBuild(targetNames, properties);
    }

    @Test
    public void testBuildWithClean() throws Exception {
        properties.put(ByggProperty.TARGET_DIR, "the target dir");

        bygg.build(true, targetNames);

        verify(cleaner).clean("the target dir");
        verify(bootstrap).startBuild(targetNames, properties);
    }
}
