/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.execution;

import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/03/2011
 */
public class Execution {
    private final TargetDAG targetDAG;

    public Execution(TargetDAG targetDAG) {
        this.targetDAG = targetDAG;
    }

    public TargetDAG dagFor(List<String> targets) {
        return null;
    }
}
