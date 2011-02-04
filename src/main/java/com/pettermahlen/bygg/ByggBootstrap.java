/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.base.Supplier;
import com.pettermahlen.bygg.configuration.ByggConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class ByggBootstrap {
    private final Supplier<ClassLoader> bootstrapClassLoaderSupplier;
    private final ConfiguredClassLoaderSource configuredClassLoaderSource;

    public ByggBootstrap(Supplier<ClassLoader> bootstrapClassLoaderSupplier, ConfiguredClassLoaderSource configuredClassLoaderSource) {
        this.bootstrapClassLoaderSupplier = bootstrapClassLoaderSupplier;
        this.configuredClassLoaderSource = configuredClassLoaderSource;
    }

    // TODO: add build targets 
    public void startBuild() throws Exception {
        // bootstrap build:
        // - get a bootstrap class loader for figuring out the configuration
        // - get a class loader that has access to the configuration for the rest of the build
        // - get the configuration instance out of the configured class loader
        // - get a build executor instance out of the configured class loader
        // - kick off the build using the executor instance and the configuration instance

        ClassLoader bootstrapClassLoader = bootstrapClassLoaderSupplier.get();

        ClassLoader configuredClassLoader = configuredClassLoaderSource.getClassLoader(bootstrapClassLoader);

        Class<Bygg> byggClass = (Class<Bygg>) configuredClassLoader.loadClass("com.pettermahlen.bygg.Bygg");

        ByggConfiguration byggConfiguration = getByggConfigurationUsingLoader(configuredClassLoader);

        Bygg bygg = byggClass.getConstructor(ByggConfiguration.class).newInstance(byggConfiguration);

        bygg.build();
    }

    private ByggConfiguration getByggConfigurationUsingLoader(ClassLoader configuredClassLoader) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class byggConfigurationClass = configuredClassLoader.loadClass("com.pettermahlen.bygg.configuration.Configuration");

        Method configurationMethod = byggConfigurationClass.getMethod("configuration", null);

        return (ByggConfiguration) configurationMethod.invoke(null, null);
    }


    public static void main(String[] args) throws Exception {
        ConfiguredClassLoaderSource janinoClassLoaderSource = new JaninoClassLoaderSource("src/test/bygg");
        ByggBootstrap bootstrap = new ByggBootstrap(new Supplier<ClassLoader>() {
            public ClassLoader get() {
                return ByggBootstrap.class.getClassLoader();
            }
        }, janinoClassLoaderSource);

        bootstrap.startBuild();
    }
}
