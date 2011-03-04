/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.configuration.ByggConfiguration;
import com.pettermahlen.bygg.configuration.ByggProperty;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ByggConfigurationLoaderImpl implements ByggConfigurationLoader {
    private static final String CONFIGURATION_CLASS_NAME = "Configuration";

    public ByggConfiguration loadConfiguration(ClassLoader classLoader) {
        // TODO: should possibly make the configuration class name configurable, although that might be taking things a step too far?
        try {
            Class byggConfigurationClass = classLoader.loadClass(CONFIGURATION_CLASS_NAME);

            Method configurationMethod = byggConfigurationClass.getMethod("configuration");

            return (ByggConfiguration) configurationMethod.invoke(null);
        } catch (ClassNotFoundException e) {
            throw new ByggException("Class '" + CONFIGURATION_CLASS_NAME + "' not found", e);
        } catch (NoSuchMethodException e) {
            throw new ByggException("Class '" + CONFIGURATION_CLASS_NAME + "' doesn't have a static method called 'configuration'", e);
        } catch (IllegalAccessException e) {
            throw new ByggException("'configuration' method in class '" + CONFIGURATION_CLASS_NAME + "' isn't accessible", e);
        } catch (InvocationTargetException e) {
            throw new ByggException("'configuration' method in class '" + CONFIGURATION_CLASS_NAME + "' threw an exception", e);
        }
    }

    public Build loadBuild(ClassLoader classLoader, ByggConfiguration byggConfiguration, Map<ByggProperty, String> properties) {
        try {
            // we know this will be a 'Build' instance, so it is safe to suppress the warning. The reason for loading it like this
            // is that the class must be loaded by a class loader that has access to the Configuration.
            @SuppressWarnings({"unchecked"}) Class<Build> buildClass = (Class<Build>) classLoader.loadClass("com.pettermahlen.bygg.Build");

            final Constructor<Build> constructor = buildClass.getConstructor(ByggConfiguration.class, Map.class);
            
            return constructor.newInstance(byggConfiguration, properties);
        } catch (Exception e) {
            // catching 'exception' here, because the types of errors that can happen above should be guaranteed not to happen
            // given the design of the 'Build' class. So I'm lumping them together - the 'cause' will have the details anyway.
            // TODO: maybe this is a case for a 'ByggError'-type class?
            throw new ByggException("'Build' class could not be instantiated - THIS SHOULD NOT BE POSSIBLE", e);
        }
    }
}
