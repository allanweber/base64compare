package com.waes.base64compare.domain.entity;

/**
 * Enum to select one side.
 */
public enum Side {
    Left(0),
    Right(1);

    private int value;

    Side(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
