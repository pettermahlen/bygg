/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.dependency;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.inject.Key;
import com.pettermahlen.bygg.Classpath;
import com.pettermahlen.bygg.Dependencies;
import com.pettermahlen.bygg.Dependency;
import com.pettermahlen.bygg.DependencySelector;

import java.util.Collection;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class CompileDependencySelector implements DependencySelector {
    public Key<Classpath> getKey() {
        return Key.get(Classpath.class, CompileTime.class);
    }

    public Collection<Dependency> select(Dependencies dependencies) {
        return Collections2.filter(dependencies.getDependencies(), new Predicate<Dependency>() {
            public boolean apply(Dependency input) {
                return input.includeIn(ClasspathType.COMPILE); 
            }
        });
    }
}
