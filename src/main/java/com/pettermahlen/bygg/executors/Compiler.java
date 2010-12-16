/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.executors;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.pettermahlen.bygg.BuildStepExecutor;
import com.pettermahlen.bygg.Reporter;
import com.pettermahlen.bygg.Sources;
import com.pettermahlen.bygg.Target;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 15, 2010
 */
public class Compiler implements BuildStepExecutor {
    private final Sources sources;
    private final Target target;

    @Inject
    public Compiler(Sources sources, Target target) {
        this.sources = sources;
        this.target = target;
    }

    public Module execute(Reporter reporter) {
//        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        throw new UnsupportedOperationException();
    }
}
