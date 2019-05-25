package com.waes.base64compare.domain.validator;

import com.waes.base64compare.Application;
import com.waes.base64compare.domain.dto.JsonData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Base64ValidationIntegratedTest {

    @Autowired
    public Validator validator;

    @Test
    public void should_return_validation_constraint_to_invalid_base64() {

        JsonData data = new JsonData("%aaa%");

        Set<ConstraintViolation<JsonData>> violations = validator.validate(data);

        assertEquals("The value must be a valid base 64 string.", violations.iterator().next().getMessage());
    }

    @Test
    public void should_return_empty_validation_constraint_to_valid_base64() {

        JsonData data = new JsonData("aaa");

        Set<ConstraintViolation<JsonData>> violations = validator.validate(data);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void should_return_empty_validation_constraint_to_null_base64() {

        JsonData data = new JsonData();

        Set<ConstraintViolation<JsonData>> violations = validator.validate(data);

        // Is returning only the validation for @NotNull
        assertEquals(1, violations.size());
        assertEquals("must not be empty", violations.iterator().next().getMessage());
    }
}