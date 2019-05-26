package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.repository.IDiffRepository;
import com.waes.base64compare.domain.service.IDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service responsible to manage Diff entities and theirs sides
 */
@Service
public class DiffService implements IDiffService {

    /**
     * Repository to save diff entity
     */
    private IDiffRepository repository;

    /**
     * Only possible constructor for the class
     * @param repository is an instance of IDiffRepository, if null a NullPointerException will be thrown
     */
    @Autowired
    public DiffService(IDiffRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DiffRepository is a required parameter.");
    }

    /**
     * Save the left side of the DiffEntity
     * @param id is the is of the DiffEntity
     * @param base64 is the value of base64 to the left side
     */
    @Override
    public void saveLeft(Long id, String base64) {
        DiffEntity entity = Optional.ofNullable(repository.get(id)).orElse(new DiffEntity(id));
        entity.setSide(Side.Left, base64);
        repository.save(entity);
    }

    /**
     * Save the left side of the DiffEntity
     * @param id is the is of the DiffEntity
     * @param base64 is the value of base64 to the right side
     */
    @Override
    public void saveRight(Long id, String base64) {
        DiffEntity entity = Optional.ofNullable(repository.get(id)).orElse(new DiffEntity(id));
        entity.setSide(Side.Right, base64);
        repository.save(entity);
    }

    /**
     * Return the result of the comparison of the sides of the DiffEntity
     * @param id is the is of the DiffEntity
     * @return the result of the comparison
     */
    @Override
    public Object getDifferences(Long id) {
        return repository.get(id);
    }
}
