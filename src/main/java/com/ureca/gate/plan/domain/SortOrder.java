package com.ureca.gate.plan.domain;

public enum SortOrder {
    ASC, DESC;

    public boolean isDesc() {
        return this == DESC;
    }
}
