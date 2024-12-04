package com.ureca.gate.plan.domain;

public enum DateFilter {
    BEFORE, AFTER;

    public boolean isBefore() {
        return this == BEFORE;
    }

    public boolean isAfter() {
        return this == AFTER;
    }
}
