package com.waes.base64compare.api;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.dto.JsonData;
import com.waes.base64compare.domain.validator.IValidator;
import com.waes.base64compare.infrastructure.service.DiffService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DiffControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    public DiffService service;

    @Mock
    public IValidator<JsonData> validator;

    @InjectMocks
    private DiffController controller;

    @Test
    public void should_throw_NullPointerException_when_instantiate_with_null_service() {
        expectedException.expectMessage("DiffService is a required dependency.");
        expectedException.expect(NullPointerException.class);
        new DiffController(null, null);
    }

    @Test
    public void should_throw_NullPointerException_when_instantiate_with_null_validtor() {
        expectedException.expectMessage("DomainValidator is a required dependency.");
        expectedException.expect(NullPointerException.class);
        new DiffController(service, null);
    }

    @Test
    public void should_return_status_201_for_sendLeft() throws URISyntaxException {
        JsonData data = new JsonData("123456");

        Mockito.doNothing().when(validator).validate(data);

        ResponseEntity response = controller.sendLeft(123L, data);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(data, response.getBody());
    }

    @Test
    public void should_return_status_201_for_sendRight() throws URISyntaxException {
        JsonData data = new JsonData("123456");

        Mockito.doNothing().when(validator).validate(data);

        ResponseEntity response = controller.sendRight(123L, data);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(data, response.getBody());
    }

    @Test
    public void should_return_200_for_getResult() {

        long id = 123L;
        DifferenceResponse differenceResponse = new DifferenceResponse(id, "123", "123");
        differenceResponse.setDifferences(Collections.singletonList(
                new Difference(0, 1)
        ));

        Mockito.when(service.getDifferences(id)).thenReturn(differenceResponse);

        ResponseEntity response = controller.getResult(id);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(differenceResponse, response.getBody());
    }
}