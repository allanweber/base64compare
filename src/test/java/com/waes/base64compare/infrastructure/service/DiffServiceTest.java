package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.repository.IDiffRepository;
import com.waes.base64compare.domain.service.IDiffService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    public IDiffRepository repository;

    @InjectMocks
    public DiffService service;

    @Test
    public void should_throw_NullPointerException_when_repositoty_is_null_when_instantiate_service(){
        expectedException.expectMessage("DiffRepository is a required parameter.");
        expectedException.expect(NullPointerException.class);
        new DiffService(null);
    }

    @Test
    public void should_save_left_with_new_entity() {
        Long id = 1L;
        String base64 = "123456";
        Mockito.when(repository.get(id)).thenReturn(null);
        service.saveLeft(id, base64);
    }

    @Test
    public void should_save_left_with_existing_entity() {
        Long id = 1L;
        String base64 = "123456";

        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Left, base64);

        Mockito.when(repository.get(id)).thenReturn(existing);
        service.saveLeft(id, base64);
    }

    @Test
    public void should_save_right_with_new_entity() {
        Long id = 1L;
        String base64 = "123456";
        Mockito.when(repository.get(id)).thenReturn(null);
        service.saveRight(id, base64);
    }

    @Test
    public void should_save_right_with_existing_entity() {
        Long id = 1L;
        String base64 = "123456";

        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Right, base64);

        Mockito.when(repository.get(id)).thenReturn(existing);
        service.saveLeft(id, base64);
    }

    @Test
    public void should_return_expected_entity() {
        Long id = 1L;
        String base64 = "123456";
        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Left, base64);
        existing.setSide(Side.Right, base64);

        Mockito.when(repository.get(id)).thenReturn(existing);

        DiffEntity returned = (DiffEntity)service.getDifferences(id);

        assertEquals(existing, returned);
    }
}