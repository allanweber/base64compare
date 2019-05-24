package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.dto.ExceptionResponse;
import com.waes.base64compare.domain.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {
            ApiException.class,
            MethodArgumentNotValidException.class
    })
    protected ResponseEntity<ExceptionResponse> handleApiException(Exception ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        return new ResponseEntity<>(
                new ExceptionResponse(String.format("Not well treated exception: %s - Exception Type: %s"
                        , ex.getMessage(),
                        ex.getClass())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
