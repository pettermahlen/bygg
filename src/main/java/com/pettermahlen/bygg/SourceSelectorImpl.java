/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import com.pettermahlen.bygg.dependency.ClasspathType;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class SourceSelectorImpl implements SourceSelector {
    private final ClasspathType classpathType;

    public SourceSelectorImpl(ClasspathType classpathType) {
        this.classpathType = classpathType;
    }
}
