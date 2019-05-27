package com.waes.base64compare.domain.validator;

import com.waes.base64compare.configuration.ValidatorConfiguration;
import com.waes.base64compare.domain.dto.JsonData;
import com.waes.base64compare.domain.exception.ApiException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DomainValidatorTest {

    private IValidator validator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup(){
        validator = new ValidatorConfiguration().getValidator();
    }

    @Test
    public void should_successfully_validate_an_object(){
        JsonData data = new JsonData("any");
        validator.validate(data);
    }

    @Test
    public void should_return_an_ApiException_when_fail_to_validate_an_object(){
        expectedException.expect(ApiException.class);
        expectedException.expectMessage("base64 must not be empty.");

        JsonData data = new JsonData();
        validator.validate(data);
    }

    @Test
    public void should_return_an_ApiException_when_fail_to_validate_an_object_null(){
        expectedException.expect(ApiException.class);
        expectedException.expectMessage("The object to be validated must not be null");

        validator.validate(null);
    }
}