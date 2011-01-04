/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Jan 4, 2011
 */
public class CompilerModifierFactory implements ModifierFactory<CompilationConfigurer> {
    @Override
    public CompilationConfigurer create(BuildStepSet buildStepSet, String stepName) {
        return new CompilationConfigurer(buildStepSet, stepName);
    }
}
