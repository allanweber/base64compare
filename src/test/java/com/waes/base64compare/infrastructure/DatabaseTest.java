package com.waes.base64compare.infrastructure;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.exception.DataBaseException;
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
public class DatabaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void hashmap_table_should_be_not_null(){
        Database database = new Database();
        Object table =  ReflectionTestUtils.getField(database, "table");
        assertNotNull(table);
    }

    @Test
    public void should_insert_value() {
        Database<DiffEntity> database = new Database<>();
        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff = new DiffEntity(1L, base64);
        database.Insert( diff);

        DiffEntity get = database.get(1L);
        assertEquals(diff, get);
    }

    @Test
    public void should_throw_DataBaseException_when_already_exist_key(){
        Database<DiffEntity> database = new Database<>();
        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff1 = new DiffEntity(1L, base64);
        database.Insert( diff1);

        base64 = new HashMap<>();
        base64.put(Side.Left, "789");
        base64.put(Side.Right, "654");
        DiffEntity diff2 = new DiffEntity(1L, base64);

        expectedException.expectMessage("Key 1 already exists.");
        expectedException.expect(DataBaseException.class);
        database.Insert(diff1);
    }

    @Test
    public void should_throw_NullPointerException_when_insert_and_entity_is_null(){
        Database<DiffEntity> database = new Database<>();
        expectedException.expectMessage("Entity must not be null.");
        expectedException.expect(NullPointerException.class);
        database.Insert(null);
    }

    @Test
    public void should_update_one_entity() {
        Database<DiffEntity> database = new Database<>();
        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff1 = new DiffEntity(1L, base64);
        database.Insert( diff1);

        base64 = new HashMap<>();
        base64.put(Side.Left, "789");
        base64.put(Side.Right, "654");
        DiffEntity diff2 = new DiffEntity(2L, base64);
        database.Insert( diff2);

        diff1.SetSide(Side.Left, "allan");
        diff1.SetSide(Side.Right, "weber");
        database.Update(diff1);

        DiffEntity get1 = database.get(1L);
        assertEquals("allan", get1.getSide(Side.Left));
        assertEquals("weber", get1.getSide(Side.Right));

        DiffEntity get2 = database.get(2L);
        assertEquals("789", get2.getSide(Side.Left));
        assertEquals("654", get2.getSide(Side.Right));
    }

    @Test
    public void should_throw_NullPointerException_when_update_and_entity_is_null(){
        Database<DiffEntity> database = new Database<>();
        expectedException.expectMessage("Entity must not be null.");
        expectedException.expect(NullPointerException.class);
        database.Update(null);
    }

    @Test
    public void should_throw_NullPointerException_when_get_and_entity_is_null(){
        Database<DiffEntity> database = new Database<>();
        expectedException.expectMessage("Key must not be null.");
        expectedException.expect(NullPointerException.class);
        database.get(null);
    }

    @Test
    public void should_return_null_when_get_and_key_does_not_exist(){
        Database<DiffEntity> database = new Database<>();
        Map<Side, String> base64 = new HashMap<>();
        base64.put(Side.Left, "123");
        base64.put(Side.Right, "465");
        DiffEntity diff1 = new DiffEntity(1L, base64);
        database.Insert( diff1);

        DiffEntity get = database.get(2L);
        assertNull(get);
    }
}