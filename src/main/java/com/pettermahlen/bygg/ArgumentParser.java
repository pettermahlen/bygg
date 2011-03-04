/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.util.Arrays;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ArgumentParser {
    private final String[] args;

    public ArgumentParser(String[] args) {
        this.args = args.clone();
    }

    public ByggArguments parse() {
        OptionParser optionParser = new OptionParser();

        OptionSpec cleanSpec = optionParser.acceptsAll(Arrays.asList("c", "clean"), "clean before building");

        OptionSet options = optionParser.parse(args);

        return new ByggArguments(options.nonOptionArguments(), options.has(cleanSpec));
    }
}
