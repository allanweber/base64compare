package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.dto.ExceptionResponse;
import com.waes.base64compare.domain.exception.ApiException;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiExceptionHandlerTest {

    @Test
    public void should_return_400_for_expected_exception() {
        ApiException ex = new ApiException("any");
        ResponseEntity response = new ApiExceptionHandler().handleApiException(ex);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("any", ((ExceptionResponse) response.getBody()).getMessage());
    }


    @Test
    public void should_return_500_for_not_expected_exception() {
        Exception ex = new Exception("any");
        ResponseEntity response = new ApiExceptionHandler().handleException(ex);
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(((ExceptionResponse) response.getBody()).getMessage().contains("Not well treated exception: any"));
    }
}