package com.waes.base64compare.domain.entity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DiffEntityTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_instantiate_new_entity() {

        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff = new DiffEntity(1L, base64);

        assertEquals("123", diff.getSide(Side.Left));
        assertEquals("465", diff.getSide(Side.Right));
    }

    @Test
    public void should_entities_be_equal() {

        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff = new DiffEntity(1L, base64);
        DiffEntity diff2 = new DiffEntity(1L, base64);

        assertEquals(diff2, diff);
    }

    @Test
    public void should_entities_be_not_equal() {

        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff = new DiffEntity(1L, base64);
        DiffEntity diff2 = new DiffEntity(2L, base64);

        assertNotEquals(diff2, diff);
    }

    @Test
    public void should_left_side_be_null() {

        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Right, "465");
        DiffEntity diff = new DiffEntity(1L, base64);

        assertNull(diff.getSide(Side.Left));
        assertEquals("465", diff.getSide(Side.Right));
    }

    @Test
    public void when_no_paramenters_constructor_id_must_be_not_null() {
        DiffEntity entity = new DiffEntity(1L);
        Object id = ReflectionTestUtils.getField(entity, "id");
        assertNotNull(id);
    }

    @Test
    public void when_no_paramenters_constructor_base64_must_be_not_null() {
        DiffEntity entity = new DiffEntity(1L);
        Object base64 = ReflectionTestUtils.getField(entity, "base64");
        assertNotNull(base64);
    }

    @Test
    public void should_throw_exception_when_id_constructor_parameter_is_null() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Id is a required parameter.");
        new DiffEntity(null, null);
    }

    @Test
    public void should_throw_exception_when_base64_constructor_parameter_is_null() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Base64 map is a required parameter.");
        new DiffEntity(1L, null);
    }
}