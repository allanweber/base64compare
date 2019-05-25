package com.waes.base64compare.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Validation implements ConstraintValidator<Base64, String> {

    @Override
    public void initialize(Base64 base64) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isValid = org.apache.commons.codec.binary.Base64.isBase64(value);

        return isValid;
    }
}
