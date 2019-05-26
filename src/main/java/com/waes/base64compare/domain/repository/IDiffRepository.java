package com.waes.base64compare.domain.repository;

import com.waes.base64compare.domain.entity.DiffEntity;

public interface IDiffRepository {

    void save(DiffEntity entity);

    DiffEntity get(Long id);
}
