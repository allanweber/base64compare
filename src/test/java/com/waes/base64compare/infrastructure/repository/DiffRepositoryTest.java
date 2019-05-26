package com.waes.base64compare.infrastructure.repository;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.repository.IDiffRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DiffRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IDiffRepository repository;

    @Before
    public void setup(){
        Database<DiffEntity> db = new Database<>();
        repository = new DiffRepository(db);
    }

    @Test
    public void should_throw_NullPointerException_when_database_is_null(){
        expectedException.expectMessage("Database is a required parameter.");
        expectedException.expect(NullPointerException.class);
        new DiffRepository(null);
    }

    @Test
    public void should_insert_new_entity() {
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Left, "123");
        diff.setSide(Side.Right, "465");

        repository.save(diff);

        DiffEntity get = repository.get(1L);
        assertEquals(diff, get);
    }

    @Test
    public void should_update_one_entity() {
        DiffEntity diff = new DiffEntity(1L);
        diff.setSide(Side.Left, "123");
        diff.setSide(Side.Right, "465");

        repository.save(diff);

        diff.setSide(Side.Left, "789");

        repository.save(diff);

        DiffEntity get = repository.get(1L);
        assertEquals("789", get.getSide(Side.Left));
    }

    @Test
    public void should_get_null_entity() {
        DiffEntity get = repository.get(1L);
        assertNull(get);
    }
}