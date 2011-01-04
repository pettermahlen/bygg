/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public class BuildStepKey<T> {
    private final String name;
    private final ModifierFactory<T> modifierFactory;

    public BuildStepKey(String name, ModifierFactory<T> modifierFactory) {
        this.name = name;
        this.modifierFactory = modifierFactory;
    }

    public ModifierFactory<T> getModifierFactory() {
        return modifierFactory;
    }

    public String getName() {
        return name;
    }
}
