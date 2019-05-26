package com.waes.base64compare.domain.validator;

/**
 * Interface to implements validation process.
 * @param <T> an Type with some validation annotation (@NotEmpty, @Base64 for example).
 */
public interface IValidator<T> {

    /**
     * Common validation method to all proposes.
     * @param data an object with some validation annotation (@NotEmpty, @Base64 for example).
     */
    void Validate(T data);

}
