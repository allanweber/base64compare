package com.waes.base64compare.domain.entity;

/**
 * Base entity to be managed by database.
 */
public abstract class BaseEnity {
    private Long id;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
