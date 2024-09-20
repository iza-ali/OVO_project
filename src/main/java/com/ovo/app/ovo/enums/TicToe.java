package com.ovo.app.ovo.enums;

public enum TicToe {
    X(1), O(2);

    private final Integer value;

    // Constructor
    TicToe(Integer value) {
        this.value = value;
    }

    // Getter method
    public Integer getValue() {
        return value;
    }
}