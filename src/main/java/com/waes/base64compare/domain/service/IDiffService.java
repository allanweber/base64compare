package com.waes.base64compare.domain.service;

public interface IDiffService {

    void saveLeft(Long id, String base64);

    void saveRight(Long id, String base64);

    Object getDifferences(Long id);
}
