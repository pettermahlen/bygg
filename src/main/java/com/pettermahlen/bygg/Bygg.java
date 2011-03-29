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
import com.pettermahlen.bygg.execution.NodeCallableFactory;
import com.pettermahlen.bygg.execution.NodeCallableFactoryImpl;

import java.net.URLClassLoader;
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
        long time = System.nanoTime();

        Map<ByggProperty, String> properties = propertiesSupplier.get();

        System.out.println("Time after properties (nms): " + (System.nanoTime() - time) / 1000000L);

        if (cleanRequired) {
            cleaner.clean(properties.get(ByggProperty.TARGET_DIR));
        }

        System.out.println("Time after clean (ms): " + (System.nanoTime() - time) / 1000000L);

        if (!targetNames.isEmpty()) {
            byggBootstrap.startBuild(targetNames, properties);
        }

        System.out.println("Time after build (ms): " + (System.nanoTime() - time) / 1000000L);
    }

    public static void main(String[] args) throws Exception {

        long time = System.nanoTime();

        ByggArguments arguments = new ArgumentParser(args).parse();

        Bygg bygg = wireUp();

        bygg.build(arguments.isClean(), arguments.getTargets());

        // TODO: this guy probably/certainly doesn't wait until the build is actuall done. Should be fixed, probably by using a better way of reporting
        System.out.println("total time taken: (ms) " + (System.nanoTime() - time) / 1000000L);      
    }

    // TODO: maybe do this using Guice or something.
    private static Bygg wireUp() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MavenArtifactClassLoaderFactory mavenArtifactClassLoaderFactory = new MavenArtifactClassLoaderFactoryImpl();
        Loader<Plugins> pluginsLoader = new MethodInvokingLoader<Plugins>("PluginConfiguration", "plugins");
        HierarchicalClassLoaderSource<ClassLoader> compilingClassLoaderSource = new JaninoClassLoaderSource("bygg");
//        HierarchicalClassLoaderSource compilingClassLoaderSource = new CompilingClassLoaderSource("bygg");
        HierarchicalClassLoaderSource<URLClassLoader> pluginClassLoaderSource = new PluginClassLoaderSource(pluginsLoader, compilingClassLoaderSource, mavenArtifactClassLoaderFactory);
        NodeCallableFactory nodeCallableFactory = new NodeCallableFactoryImpl();
        ByggConfigurationLoader configurationLoader = new ByggConfigurationLoaderImpl(executorService, nodeCallableFactory);
        ByggBootstrap bootstrap = new ByggBootstrap(Bygg.class.getClassLoader(), pluginClassLoaderSource, compilingClassLoaderSource, configurationLoader);

        Cleaner cleaner = new CleanerImpl();
        Supplier<Map<ByggProperty, String>> byggPropertySupplier = new ByggPropertySupplier();

        return new Bygg(byggPropertySupplier, cleaner, bootstrap);
    }
}
