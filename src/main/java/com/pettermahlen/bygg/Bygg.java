/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.google.common.base.Supplier;
import com.pettermahlen.bygg.bootstrap.*;
import com.pettermahlen.bygg.configuration.ByggProperty;
import com.pettermahlen.bygg.configuration.ByggPropertySupplier;
import com.pettermahlen.bygg.configuration.Plugins;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 07/02/2011
 */
public class Bygg {
    private final Supplier<Map<ByggProperty, String>> propertiesSupplier;
    private final Cleaner cleaner;
    private final ByggBootstrap byggBootstrap;


    public Bygg(Supplier<Map<ByggProperty, String>> propertiesSupplier, Cleaner cleaner, ByggBootstrap byggBootstrap) {
        this.propertiesSupplier = propertiesSupplier;
        this.cleaner = cleaner;
        this.byggBootstrap = byggBootstrap;
    }

    public void build(boolean cleanRequired, List<String> targetNames) throws Exception {
        Map<ByggProperty, String> properties = propertiesSupplier.get();

        if (cleanRequired) {
            cleaner.clean(properties.get(ByggProperty.TARGET_DIR));
        }

        if (!targetNames.isEmpty()) {
            byggBootstrap.startBuild(targetNames, properties);
        }
    }

    public static void main(String[] args) throws Exception {
        ByggArguments arguments = new ArgumentParser(args).parse();

        Bygg bygg = wireUp();

        bygg.build(arguments.isClean(), arguments.getTargets());
    }

    // TODO: maybe do this using Guice or something.
    private static Bygg wireUp() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MavenArtifactClassLoaderFactory mavenArtifactClassLoaderFactory = new MavenArtifactClassLoaderFactoryImpl();
        Loader<Plugins> pluginsLoader = new MethodInvokingLoader<Plugins>("PluginConfiguration", "plugins");
        HierarchicalClassLoaderSource compilingClassLoaderSource = new JaninoClassLoaderSource("bygg");
        HierarchicalClassLoaderSource pluginClassLoaderSource = new PluginClassLoaderSource(pluginsLoader, compilingClassLoaderSource, mavenArtifactClassLoaderFactory);
        ByggConfigurationLoader configurationLoader = new ByggConfigurationLoaderImpl(executorService);
        ByggBootstrap bootstrap = new ByggBootstrap(Bygg.class.getClassLoader(), pluginClassLoaderSource, compilingClassLoaderSource, configurationLoader);

        Cleaner cleaner = new CleanerImpl();
        Supplier<Map<ByggProperty, String>> byggPropertySupplier = new ByggPropertySupplier();

        return new Bygg(byggPropertySupplier, cleaner, bootstrap);
    }
}
