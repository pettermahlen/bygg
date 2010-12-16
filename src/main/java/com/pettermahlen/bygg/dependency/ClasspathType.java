/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.dependency;

import com.google.inject.Key;
import com.pettermahlen.bygg.Classpath;

import java.lang.annotation.Annotation;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public enum ClasspathType {
    PRODUCT(Compile.class),
    TEST(Test.class),
    PACKAGE(Package.class);

    private final Class<? extends Annotation> annotation;

    ClasspathType(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public Key<Classpath> getKey() {
        return Key.get(Classpath.class, annotation);
    }
}
