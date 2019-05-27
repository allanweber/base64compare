package com.waes.base64compare.domain.service;

import com.waes.base64compare.domain.dto.DifferenceResponse;

/**
 * Domain interface to implement diff service management.
 */
public interface IDiffService {

    /**
     * Save left side of one difference verification.
     * @param id of the difference transaction.
     * @param base64 value of base64 for left side.
     */
    void saveLeft(Long id, String base64);

    /**
     * Save right side of one difference verification.
     * @param id of the difference transaction.
     * @param base64 value of base64 for right side.
     */
    void saveRight(Long id, String base64);

    /**
     * Get the result of difference evaluation
     * @param id  of the difference transaction.
     * @return data regarding to difference evaluation.
     * @see  com.waes.base64compare.domain.dto.DifferenceResponse
     */
    DifferenceResponse getDifferences(Long id);
}
