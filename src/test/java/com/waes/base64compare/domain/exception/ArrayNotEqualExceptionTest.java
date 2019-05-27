package com.waes.base64compare.domain.exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArrayNotEqualExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_return_expected_message_when_throw_exception() {
        expectedException.expectMessage("Arrays have different length: 5 to the Left and 6 to the Right.");
        expectedException.expect(ArrayNotEqualException.class);
        throw new ArrayNotEqualException(5, 6);
    }
}