package com.waes.base64compare.domain.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This entity will maintain the data os base64 sides to compare.
 */
public class DiffEntity extends BaseEnity {

    /**
     * Map to store all sides of the entity.
     */
    private Map<Side, String> base64;

    /**
     * Constructor used to manage the map lately.
     * @param id unique to store, if null a NullPointerException will be thrown
     */
    public DiffEntity(Long id) {
        setId(Objects.requireNonNull(id, "Id is a required parameter."));
        this.base64 = new HashMap<>();
    }

    /**
     * Set one side of entity.
     *
     * @param side   Side to set value. Throw exception when it is null.
     * @param base64 value of the side. Throw exception when it is null.
     */
    public void setSide(Side side, String base64) {
        Objects.requireNonNull(side, "Side is a required argument.");
        Objects.requireNonNull(base64, "Base64 is a required argument.");
        this.base64.put(side, base64);
    }

    /**
     * Get value from one side.
     *
     * @param side to get the value.
     * @return base64 value of the side, can return null if the side was not set.
     */
    public String getSide(Side side) {
        return this.base64.get(side);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiffEntity that = (DiffEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(base64, that.base64);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), base64);
    }

}
