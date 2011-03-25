/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.google.common.base.Preconditions;
import com.pettermahlen.bygg.Build;
import com.pettermahlen.bygg.ByggException;
import com.pettermahlen.bygg.NodeCallableFactory;
import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggConfigurationLoaderImpl extends MethodInvokingLoader<ByggConfiguration> implements ByggConfigurationLoader {
    private final ExecutorService executorService;
    private final NodeCallableFactory nodeCallableFactory;

    public ByggConfigurationLoaderImpl(ExecutorService executorService, NodeCallableFactory nodeCallableFactory) {
        super("Configuration", "configuration");
        this.executorService = Preconditions.checkNotNull(executorService, "executorService");
        this.nodeCallableFactory = Preconditions.checkNotNull(nodeCallableFactory, "nodeCallableFactory");
    }

    // TODO: this isn't perfect, but I can probably revisit that later.
    public ByggConfiguration loadConfiguration(ClassLoader classLoader) {
        return load(classLoader);
    }

    public Build loadBuild(ClassLoader classLoader, ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties) {
        try {
            // we know this will be a 'Build' instance, so it is safe to suppress the warning. The reason for loading it like this
            // is that the class must be loaded by a class loader that has access to the Configuration.
            @SuppressWarnings({"unchecked"}) Class<Build> buildClass = (Class<Build>) classLoader.loadClass("com.pettermahlen.bygg.Build");

            final Constructor<Build> constructor = buildClass.getConstructor(ByggConfiguration.class, Map.class, ExecutorService.class, NodeCallableFactory.class);
            
            return constructor.newInstance(byggConfiguration, properties, executorService, nodeCallableFactory);
        } catch (Exception e) {
            // catching 'exception' here, because the types of errors that can happen above should be guaranteed not to happen
            // given the design of the 'Build' class. So I'm lumping them together - the 'cause' will have the details anyway.
            // TODO: maybe this is a case for a 'ByggError'-type class?
            throw new ByggException("'Build' class could not be instantiated - THIS SHOULD NOT BE POSSIBLE", e);
        }
    }

}
