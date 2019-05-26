package com.waes.base64compare.domain.entity;

import java.util.Objects;

/**
 * Entity to store data for one side.
 */
public class SideEntity {

    /**
     * Side enum.
     */
    private Side side;

    /**
     * Value of the side.
     */
    private String value;

    /**
     * Only possible constructor setting both, side and value.
     * @param side to set value.
     * @param value of the side.
     */
    public SideEntity(Side side, String value) {
        this.side = Objects.requireNonNull(side, "Side is a required parameter.");
        this.value = Objects.requireNonNull(value, "Value is a required parameter.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SideEntity that = (SideEntity) o;
        return side == that.side &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, value);
    }
}
