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
     * @param id unique to store.
     */
    public DiffEntity(Long id) {
        setId(id);
        this.base64 = new HashMap<>();
    }

    /**
     * Constructor used to manage map.
     * @param id unique to store.
     * @param base64 can set both, none, or only one side.
     */
    public DiffEntity(Long id, Map<Side, String> base64) {
        this.setId(Objects.requireNonNull(id, "Id is a required parameter."));
        this.base64 = Objects.requireNonNull(base64, "Base64 map is a required parameter.");
    }

    /**
     * Set one side of entity.
     * @param side Side to set value.
     * @param base64 value of the side.
     */
    public void SetSide(Side side, String base64) {
        this.base64.put(side, base64);
    }

    /**
     * Get value from one side.
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
