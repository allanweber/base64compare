package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.validator.IValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorConfigurationTest {

    @Test
    public void should_return_an_instance_of_DomainValidator() {
        IValidator validator = new ValidatorConfiguration().getValidator();
        assertEquals("com.waes.base64compare.domain.validator.DomainValidator", validator.getClass().getName());
    }
}