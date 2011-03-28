/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public interface HierarchicalClassLoaderSource<T extends ClassLoader> {
    T getClassLoader(ClassLoader parent);
}
