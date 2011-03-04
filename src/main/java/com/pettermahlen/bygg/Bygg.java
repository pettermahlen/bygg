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
        // read properties
        // do clean if specified
        // kick off regular build if specified
        Map<ByggProperty, String> properties = propertiesSupplier.get();

        if (cleanRequired) {
            cleaner.clean(properties.get(ByggProperty.TARGET_DIR));
        }

        if (!targetNames.isEmpty()) {
            byggBootstrap.startBuild(targetNames, properties);
        }
    }

    public static void main(String[] args) throws Exception {
        // parse cmd-line args
        // initialise a Bygg instance using guice?!
        // kick off

        ByggArguments arguments = new ArgumentParser(args).parse();

        Bygg bygg = wireUp();

        bygg.build(arguments.isClean(), arguments.getTargets());
    }

    private static Bygg wireUp() {
        MavenArtifactClassLoaderFactory mavenArtifactClassLoaderFactory = new MavenArtifactClassLoaderFactoryImpl();
        Loader<Plugins> pluginsLoader = new MethodInvokingLoader<Plugins>("Plugins", "plugins");
        HierarchicalClassLoaderSource pluginClassLoaderSource = new PluginClassLoaderSource(pluginsLoader, mavenArtifactClassLoaderFactory);
        HierarchicalClassLoaderSource configuredClassLoaderSource = new JaninoClassLoaderSource("bygg");
        ByggConfigurationLoader configurationLoader = new ByggConfigurationLoaderImpl();
        ByggBootstrap bootstrap = new ByggBootstrap(Bygg.class.getClassLoader(), pluginClassLoaderSource, configuredClassLoaderSource, configurationLoader);

        Cleaner cleaner = new CleanerImpl();
        Supplier<Map<ByggProperty, String>> byggPropertySupplier = new ByggPropertySupplier();

        return new Bygg(byggPropertySupplier, cleaner, bootstrap);
    }
}
