/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.steps;

import com.pettermahlen.bygg.BuildResult;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 28, 2010
 */
public enum StandardBuildResults implements BuildResult {
    MAIN_CLASSPATH,
    MAIN_SOURCES,
    MAIN_CLASSES,
    TEST_CLASSPATH,
    TEST_SOURCES,
    TEST_CLASSES,
    TEST_RESULT,
}
