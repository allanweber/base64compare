package com.waes.base64compare.infrastructure.repository;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.repository.IDiffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * Repository responsible for manage diff data
 */
@Repository
public class DiffRepository implements IDiffRepository {

    /**
     * Database management
     */
    private Database<DiffEntity> database;

    /**
     * Only possible constructor, if database argument is null a null exception will thrown.
     *
     * @param database an instance of Database<DiffEntity>. In spring context will be created by
     *                 'DiffDatabase' qualified bean.
     */
    @Autowired
    public DiffRepository(
            @Qualifier("DiffDatabase") Database<DiffEntity> database) {
        this.database = Objects.requireNonNull(database, "Database is a required parameter.");
    }

    /**
     * insert new data
     *
     * @param entity a valid DiffEntity object
     */
    @Override
    public void save(DiffEntity entity) {
        database.save(entity);
    }

    /**
     * Return a diff entity by id
     *
     * @param id of the entity
     * @return DiffEntity from database
     */
    @Override
    public DiffEntity get(Long id) {
        return database.get(id);
    }
}
