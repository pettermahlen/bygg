/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 14, 2010
 */
public interface TestRunner {
    TestResult runTests(Classes productionClasses, Classes testClasses);
}
