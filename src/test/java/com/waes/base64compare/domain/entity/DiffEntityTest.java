package com.waes.base64compare.domain.entity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DiffEntityTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_instantiate_new_entity() {
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Left, "123");
        diff.setSide(Side.Right, "465");

        assertEquals("123", diff.getSide(Side.Left));
        assertEquals("465", diff.getSide(Side.Right));
    }

    @Test
    public void should_entities_be_equal() {
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Left, "123");
        diff.setSide(Side.Right, "465");

        DiffEntity diff2 = new DiffEntity(1L);
        diff2.setSide(Side.Left, "123");
        diff2.setSide(Side.Right, "465");

        assertEquals(diff2, diff);
    }

    @Test
    public void should_entities_be_not_equal() {
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Left, "123");
        diff.setSide(Side.Right, "465");

        DiffEntity diff2 = new DiffEntity(1L);
        diff2.setSide(Side.Left, "123");
        diff2.setSide(Side.Right, "789");

        assertNotEquals(diff2, diff);
    }

    @Test
    public void should_left_side_be_null() {
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Right, "465");

        assertNull(diff.getSide(Side.Left));
        assertEquals("465", diff.getSide(Side.Right));
    }

    @Test
    public void when_no_parameters_constructor_id_must_be_not_null() {
        DiffEntity entity = new DiffEntity(1L);
        Object id = ReflectionTestUtils.getField(entity, "id");
        assertNotNull(id);
    }

    @Test
    public void when_no_parameters_constructor_base64_must_be_not_null() {
        DiffEntity entity = new DiffEntity(1L);
        Object base64 = ReflectionTestUtils.getField(entity, "base64");
        assertNotNull(base64);
    }

    @Test
    public void should_throw_exception_when_id_constructor_parameter_is_null() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Id is a required parameter.");
        new DiffEntity(null);
    }

    @Test
    public void should_throw_exception_when_setSide_with_null_side(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Side is a required argument.");
        DiffEntity entity = new DiffEntity(1L);
        entity.setSide(null , null);
    }

    @Test
    public void should_throw_exception_when_setSide_with_null_value(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Base64 is a required argument.");
        DiffEntity entity = new DiffEntity(1L);
        entity.setSide(Side.Left , null);
    }
}