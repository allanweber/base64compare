package com.waes.base64compare.domain.dto;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DifferenceResponseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_throw_NullPointerException_when_instantiate_with_null_id() {
        expectedException.expectMessage("Id is a required argument.");
        expectedException.expect(NullPointerException.class);
        new DifferenceResponse(null, null, null);
    }

    @Test
    public void should_throw_NullPointerException_when_instantiate_with_null_left() {
        expectedException.expectMessage("Left is a required argument.");
        expectedException.expect(NullPointerException.class);
        new DifferenceResponse(1L, null, null);
    }

    @Test
    public void should_throw_NullPointerException_when_instantiate_with_null_right() {
        expectedException.expectMessage("Right is a required argument.");
        expectedException.expect(NullPointerException.class);
        new DifferenceResponse(1L, "123", null);
    }

    @Test
    public void should_instantiate_with_false_equal_and_equalSize() {
        DifferenceResponse response = new DifferenceResponse(1L, "123", "123");
        assertFalse(response.getEqual());
        assertFalse(response.getEqualSize());
    }
}