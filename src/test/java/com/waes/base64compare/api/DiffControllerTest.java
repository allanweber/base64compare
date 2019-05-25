package com.waes.base64compare.api;

import com.waes.base64compare.domain.dto.JsonData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class DiffControllerTest {

    private DiffController controller;

    @Before
    public void setup() {
        controller = new DiffController();
    }

    @Test
    public void should_return_status_201_for_sendLeft() throws URISyntaxException {
        JsonData data = new JsonData("123456");
        ResponseEntity response = controller.sendLeft("123", data);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void should_return_status_201_for_sendRight() throws URISyntaxException {
        JsonData data = new JsonData("123456");
        ResponseEntity response = controller.sendRight("123", data);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void should_return_200_for_getResult() {
        ResponseEntity response = controller.getResult("123");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }
}