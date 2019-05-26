package com.waes.base64compare.domain.entity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class SideEntityTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_instantiate_new_side(){
        SideEntity side = new SideEntity(Side.Left, "123");
    }

    @Test
    public void should_entities_be_equal(){
        SideEntity side = new SideEntity(Side.Left, "123");
        SideEntity side2 = new SideEntity(Side.Left, "123");

        assertEquals(side, side2);
    }

    @Test
    public void should_entities_be_not_equal(){
        SideEntity side = new SideEntity(Side.Left, "123");
        SideEntity side2 = new SideEntity(Side.Left, "1234");

        assertNotEquals(side, side2);
    }

    @Test
    public void should_entities_be_not_equal_side(){
        SideEntity side = new SideEntity(Side.Left, "123");
        SideEntity side2 = new SideEntity(Side.Right, "123");

        assertNotEquals(side, side2);
    }

    @Test
    public void should_throw_exception_when_side_constructor_parameter_is_null() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Side is a required parameter.");
        new SideEntity(null, null);
    }

    @Test
    public void should_throw_exception_when_value_constructor_parameter_is_null() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Value is a required parameter.");
        new SideEntity(Side.Left, null);
    }
}