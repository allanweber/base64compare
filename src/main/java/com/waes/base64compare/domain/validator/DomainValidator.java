package com.waes.base64compare.domain.validator;

import com.waes.base64compare.domain.exception.ApiException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

/**
 * The implementation of IValidator to validate objects according to its rules.
 * @param <T> an Type with some validation annotation (@NotEmpty, @Base64 for example).
 */
public class DomainValidator<T> implements IValidator<T> {

    private Validator validator;

    /**
     * Default constructor instancing the validator context.
     */
    public DomainValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Validate action, in case of violations in the data expected, an ApiException will be thrown.
     * @param data an object with some validation annotation (@NotEmpty, @Base64 for example).
     */
    public void Validate(T data) {

        Set<ConstraintViolation<T>> violations = validator.validate(
                Optional.ofNullable(data).orElseThrow(
                () -> new ApiException("The object to be validated must not be null")
        ));

        if (!violations.isEmpty()) {
            ConstraintViolation<T> violation = violations.iterator().next();
            throw new ApiException(String.format("%s %s.", violation.getPropertyPath(), violation.getMessage()));
        }
    }
}
