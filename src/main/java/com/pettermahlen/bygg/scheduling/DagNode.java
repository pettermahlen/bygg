/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.scheduling;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since Dec 27, 2010
 */
public class DagNode<T> {
    private final ImmutableSet<DagNode<T>> edges;
    private final T payload;

    public DagNode(Iterable<DagNode<T>> edges, T payload) {
        this.edges = ImmutableSet.copyOf(edges);
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(edges, payload);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DagNode)) {
            return false;
        }

        DagNode other = (DagNode) o;

        return Objects.equal(edges, other.edges) && Objects.equal(payload, other.payload);
    }

    @Override
    public String toString() {
        return "DagNode{" +
                "payload=" + payload +
                ", edges=" + edges +
                '}';
    }

    public Set<DagNode<T>> getEdges() {
        return edges;
    }
}
