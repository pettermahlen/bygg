/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class ArgumentParserTest {
    ArgumentParser parser;

    @Test
    public void testParseNoClean() throws Exception {
        parser = new ArgumentParser(new String[] { "compile", "test"});

        ByggArguments arguments = parser.parse();

        assertThat(arguments.getTargets(), equalTo(Arrays.asList("compile", "test")));
        assertFalse(arguments.isClean());
    }

    @Test
    public void testParseShortClean1() throws Exception {
        parser = new ArgumentParser(new String[] { "compile", "test", "-c"});

        ByggArguments arguments = parser.parse();

        assertThat(arguments.getTargets(), equalTo(Arrays.asList("compile", "test")));
        assertTrue(arguments.isClean());
    }

    @Test
    public void testParseShortClean2() throws Exception {
        parser = new ArgumentParser(new String[] { "-c", "compile", "test" });

        ByggArguments arguments = parser.parse();

        assertThat(arguments.getTargets(), equalTo(Arrays.asList("compile", "test")));
        assertTrue(arguments.isClean());
    }

    @Test
    public void testParseLongClean() throws Exception {
        parser = new ArgumentParser(new String[] { "--clean", "compile", "test"});

        ByggArguments arguments = parser.parse();

        assertThat(arguments.getTargets(), equalTo(Arrays.asList("compile", "test")));
        assertTrue(arguments.isClean());
    }
}
