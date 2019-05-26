package com.waes.base64compare.infrastructure;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.exception.DataBaseException;
import com.waes.base64compare.infrastructure.repository.Database;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
    public void should_save_value() {
        Database<DiffEntity> database = new Database<>();
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Left, "123");
        diff.setSide(Side.Right, "465");
        database.save( diff);

        DiffEntity get = database.get(1L);
        assertEquals(diff, get);
    }

    @Test
    public void should_throw_NullPointerException_when_insert_and_entity_is_null(){
        Database<DiffEntity> database = new Database<>();
        expectedException.expectMessage("Entity must not be null.");
        expectedException.expect(NullPointerException.class);
        database.save(null);
    }

    @Test
    public void should_throw_NullPointerException_when_insert_and_entity_id_is_null(){
        Database<DiffEntity> database = new Database<>();
        expectedException.expectMessage("Entity key must not be null.");
        expectedException.expect(NullPointerException.class);
        DiffEntity entity = new DiffEntity(1L);
        ReflectionTestUtils.setField(entity,"id", null);
        database.save(entity);
    }

    @Test
    public void should_update_one_entity() {
        Database<DiffEntity> database = new Database<>();
        DiffEntity diff1 = new DiffEntity(1L);
        diff1.setSide(Side.Left, "123");
        diff1.setSide(Side.Right, "465");
        database.save( diff1);

        DiffEntity diff2 = new DiffEntity(2L);
        diff2.setSide(Side.Left, "789");
        diff2.setSide(Side.Right, "654");
        database.save( diff2);

        diff1.setSide(Side.Left, "allan");
        diff1.setSide(Side.Right, "weber");
        database.save(diff1);

        DiffEntity get1 = database.get(1L);
        assertEquals("allan", get1.getSide(Side.Left));
        assertEquals("weber", get1.getSide(Side.Right));

        DiffEntity get2 = database.get(2L);
        assertEquals("789", get2.getSide(Side.Left));
        assertEquals("654", get2.getSide(Side.Right));
    }

    @Test
    public void should_throw_NullPointerException_when_get_and_entity_id_is_null(){
        Database<DiffEntity> database = new Database<>();
        expectedException.expectMessage("Key must not be null.");
        expectedException.expect(NullPointerException.class);
        database.get(null);
    }

}