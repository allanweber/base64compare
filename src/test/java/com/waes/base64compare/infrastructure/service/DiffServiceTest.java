package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.repository.IDiffRepository;
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
    public void should_throw_NullPointerException_when_repository_is_null_when_instantiate_service(){
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
    public void should_throw_NullPointerException_when_getting_difference_and_right_is_null(){
        Long id = 1L;
        String base64 = "123456";
        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Left, base64);

        Mockito.when(repository.get(id)).thenReturn(existing);

        expectedException.expectMessage("The entity 1 does not have the Right side.");
        expectedException.expect(NullPointerException.class);
        service.getDifferences(id);
    }

    @Test
    public void should_throw_NullPointerException_when_getting_difference_and_left_is_null(){
        Long id = 1L;
        String base64 = "123456";
        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Right, base64);

        Mockito.when(repository.get(id)).thenReturn(existing);

        expectedException.expectMessage("The entity 1 does not have the Left side.");
        expectedException.expect(NullPointerException.class);
        service.getDifferences(id);
    }

    @Test
    public void should_return_equal_when_getting_difference_with_both_sides_equals(){
        Long id = 1L;
        String base64 = "123456";
        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Left, base64);
        existing.setSide(Side.Right, base64);

        Mockito.when(repository.get(id)).thenReturn(existing);
        DifferenceResponse response = service.getDifferences(id);
        assertEquals(id, response.getId());
        assertTrue(response.getEqual());
        assertTrue(response.getEqualSize());
        assertEquals("123456", response.getLeft());
        assertEquals("123456", response.getRight());
    }

    @Test
    public void should_return_not_equal_size_when_getting_difference_with_both_sides_different(){
        Long id = 1L;
        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Left, "123456");
        existing.setSide(Side.Right, "6542310");

        Mockito.when(repository.get(id)).thenReturn(existing);
        DifferenceResponse response = service.getDifferences(id);
        assertEquals(id, response.getId());
        assertFalse(response.getEqualSize());
        assertFalse(response.getEqual());
        assertEquals("123456", response.getLeft());
        assertEquals("6542310", response.getRight());
    }

    @Test
    public void should_return_equal_but_different_size_when_getting_difference(){
        Long id = 1L;
        DiffEntity existing = new DiffEntity(id);
        existing.setSide(Side.Left, "YWxsYW4gd2ViZXI=");
        existing.setSide(Side.Right, "YWNjYW4gd2ViZXI=");

        Mockito.when(repository.get(id)).thenReturn(existing);
        DifferenceResponse response = service.getDifferences(id);

        assertEquals(id, response.getId());
        assertTrue(response.getEqualSize());
        assertFalse(response.getEqual());

        Difference difference = response.getDifferences().iterator().next();
        assertEquals(1,response.getDifferences().size());
        assertEquals(1,difference.getPosition());
        assertEquals(2,difference.getOffSet());

        assertEquals("YWxsYW4gd2ViZXI=", response.getLeft());
        assertEquals("YWNjYW4gd2ViZXI=", response.getRight());
    }
}