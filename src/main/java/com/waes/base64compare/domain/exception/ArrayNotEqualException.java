package com.waes.base64compare.domain.exception;

/**
 * Exception to throw when arrays have different lengths when compared.
 */
public class ArrayNotEqualException extends RuntimeException {

    /**
     * Only possible constructor setting default message with array' lengths.
     *
     * @param leftLength  is the length of left side.
     * @param rightLength is the right of left side.
     */
    public ArrayNotEqualException(int leftLength, int rightLength) {
        super(String.format("Arrays have different length: %s to the Left and %s to the Right.", leftLength, rightLength));
    }
}
