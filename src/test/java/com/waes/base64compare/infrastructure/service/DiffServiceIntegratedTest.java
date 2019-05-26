package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.Application;
import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DiffServiceIntegratedTest {

    @Autowired
    private DiffService service;

    @Test
    public void should_save_a_new_entity() {
        Long id = 100L;

        DiffEntity nullDiff = (DiffEntity) service.getDifferences(id);
        assertNull(nullDiff);
        service.saveLeft(id, "123");

        DiffEntity notNullDiff = (DiffEntity) service.getDifferences(id);
        assertNotNull(notNullDiff);
        assertEquals("123", notNullDiff.getSide(Side.Left));
        assertNull(notNullDiff.getSide(Side.Right));
    }

    @Test
    public void should_save_a_new_entity_and_update_it() {

        Long id = 100L;
        service.saveLeft(id, "123");

        DiffEntity inserted = (DiffEntity) service.getDifferences(id);
        assertNotNull(inserted);
        assertEquals("123", inserted.getSide(Side.Left));
        assertNull(inserted.getSide(Side.Right));

        service.saveRight(id, "456");
        DiffEntity updated = (DiffEntity) service.getDifferences(id);
        assertNotNull(updated);
        assertEquals("123", inserted.getSide(Side.Left));
        assertEquals("456", inserted.getSide(Side.Right));
    }

    @Test
    public void should_manage_two_incomplet_entities(){
        Long idLeft = 100L;
        service.saveLeft(idLeft, "123");

        Long idRight = 200L;
        service.saveRight(idRight, "456");

        DiffEntity left = (DiffEntity) service.getDifferences(idLeft);
        assertNotNull(left);
        assertEquals("123", left.getSide(Side.Left));
        assertNull(left.getSide(Side.Right));

        DiffEntity right = (DiffEntity) service.getDifferences(idRight);
        assertNotNull(right);
        assertEquals("456", right.getSide(Side.Right));
        assertNull(right.getSide(Side.Left));
    }
}