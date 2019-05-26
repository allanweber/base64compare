package com.waes.base64compare.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the Constraint to validate base 64 data format.
 */
public class Base64Validation implements ConstraintValidator<Base64, String> {

    @Override
    public void initialize(Base64 base64) {
    }

    /**
     * Uses apache coded to validate base 64 data.
     * @param value the value to validate.
     * @param context an instance of javax.validation.Validator.
     * @return Boolean indicating if the value is valid, in case of value null, this is valid.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null)
            return true;

        boolean isValid = org.apache.commons.codec.binary.Base64.isBase64(value);

        return isValid;
    }
}
