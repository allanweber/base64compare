package com.waes.base64compare.domain.validator;

import com.waes.base64compare.domain.exception.ApiException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

public class DomainValidator<T> implements IValidator<T> {
    private Validator validator;

    public DomainValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

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
