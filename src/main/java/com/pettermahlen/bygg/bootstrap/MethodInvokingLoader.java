/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.pettermahlen.bygg.ByggException;
import com.pettermahlen.bygg.configuration.ByggConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class MethodInvokingLoader<T> implements Loader<T> {
    private final String sourceClassName;
    private final String methodName;

    public MethodInvokingLoader(String sourceClassName, String methodName) {
        this.sourceClassName = sourceClassName;
        this.methodName = methodName;
    }

    public T load(ClassLoader classLoader) {
        return invokeMethod(classLoader, sourceClassName, methodName);
    }

    protected final T invokeMethod(ClassLoader classLoader, String sourceClassName, String methodName) {
        try {
            Class sourceClass = classLoader.loadClass(sourceClassName);

            Method sourceMethod = sourceClass.getMethod(methodName);

            //noinspection unchecked
            return (T) sourceMethod.invoke(null);
        } catch (ClassNotFoundException e) {
            throw new ByggException("Class '" + sourceClassName + "' not found", e);
        } catch (NoSuchMethodException e) {
            throw new ByggException("Class '" + sourceClassName + "' doesn't have a static method called '" + methodName + "'", e);
        } catch (IllegalAccessException e) {
            throw new ByggException("'" + methodName + "' method in class '" + sourceClassName + "' isn't accessible", e);
        } catch (InvocationTargetException e) {
            throw new ByggException("'" + methodName + "' method in class '" + sourceClassName + "' threw an exception", e);
        }
    }
}
