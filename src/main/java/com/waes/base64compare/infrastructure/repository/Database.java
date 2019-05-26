package com.waes.base64compare.infrastructure.repository;

import com.waes.base64compare.domain.entity.BaseEnity;
import com.waes.base64compare.domain.exception.DataBaseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Fake database with the only propose to manage differ entities in memory.
 * @param <T> Type based on BaseEntity.
 */
public class Database<T extends BaseEnity> {

    /**
     * Table stored.
     */
    private Map<Long, T> table;

    /**
     * Only constructor instantiating the table map.
     */
    public Database() {
        this.table = new HashMap<>();
    }

    /**
     * Insert new entity in the store.
     * @param entity Object based on BaseEntity. Must not be null. If Object is null throws NullPointerException.
     *               If the key already exists throws DataBaseException.
     */
    public void Insert(T entity){
        Objects.requireNonNull(entity, "Entity must not be null.");

        if(table.containsKey(entity.getId()))
            throw new DataBaseException(String.format("Key %s already exists.", entity.getId()));

        table.put(entity.getId(), entity);
    }

    /**
     * Update one entity
     * @param entity Object based on BaseEntity. Must not be null. If Object is null throws NullPointerException.
     *               If the key does not exists throws DataBaseException.
     */
    public void Update(T entity){
        Objects.requireNonNull(entity, "Entity must not be null.");

        if(!table.containsKey(entity.getId()))
            throw new DataBaseException(String.format("Key %s does not exists.", entity.getId()));

        table.put(entity.getId(), entity);
    }

    /**
     * Get the entity stored in one key
     * @param key Key must not be null. If key is null throws NullPointerException.
     * @return An entity os null id the key does not exists
     */
    public T get(Long key){
        Objects.requireNonNull(key, "Key must not be null.");
        return table.get(key);
    }
}
