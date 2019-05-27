package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.Application;
import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DiffServiceIntegratedTest {

    @Autowired
    private DiffService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_throw_NullPointerException_getting_difference_there_is_not_right_side() {
        Long id = 100L;
        service.saveLeft(id, "YWxsYW4gY2Fzc2lhbm8gd2ViZXI=");

        expectedException.expectMessage("The entity 100 does not have the Right side.");
        expectedException.expect(NullPointerException.class);
        service.getDifferences(id);
    }

    @Test
    public void should_throw_NullPointerException_getting_difference_there_is_not_left_side() {
        Long id = 100L;
        service.saveRight(id, "YWxsYW4gY2Fzc2lhbm8gd2ViZXI=");

        expectedException.expectMessage("The entity 100 does not have the Left side.");
        expectedException.expect(NullPointerException.class);
        service.getDifferences(id);
    }

    @Test
    public void should_save_a_new_entity_and_update_it() {

        String base64 = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        Long id = 100L;
        service.saveLeft(id, base64);
        service.saveRight(id, base64);

        DifferenceResponse response = service.getDifferences(id);
        assertEquals(id, response.getId());
    }

    @Test
    public void should_return_response_with_equal_and_equalSize_true_and_null_differences() {

        String base64 = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        Long id = 100L;
        service.saveLeft(id, base64);
        service.saveRight(id, base64);

        DifferenceResponse response = service.getDifferences(id);
        assertEquals(id, response.getId());
        assertTrue(response.getEqual());
        assertTrue(response.getEqualSize());
        assertNull(response.getDifferences());
    }

    @Test
    public void should_return_response_with_not_equal_and_not_equalSize_and_null_differences() {

        String base64Left = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        String base64Right = Base64.getEncoder().encodeToString("Allan Cassiano Weber".getBytes());
        Long id = 100L;
        service.saveLeft(id, base64Left);
        service.saveRight(id, base64Right);

        DifferenceResponse response = service.getDifferences(id);
        assertEquals(id, response.getId());
        assertFalse(response.getEqual());
        assertFalse(response.getEqualSize());
        assertNull(response.getDifferences());
    }

    @Test
    public void should_return_response_equal_size_and_list_of_differences() {

        String base64Left = Base64.getEncoder().encodeToString("Allan Weber Cassiano".getBytes());
        String base64Right = Base64.getEncoder().encodeToString("Allan Cassiano Weber".getBytes());
        Long id = 100L;
        service.saveLeft(id, base64Left);
        service.saveRight(id, base64Right);

        DifferenceResponse response = service.getDifferences(id);
        assertEquals(id, response.getId());
        assertFalse(response.getEqual());
        assertTrue(response.getEqualSize());

        assertNotNull(response.getDifferences());

        Difference difference = response.getDifferences().iterator().next();
        assertEquals(6,difference.getPosition());
        assertEquals(14,difference.getOffSet());
    }
}