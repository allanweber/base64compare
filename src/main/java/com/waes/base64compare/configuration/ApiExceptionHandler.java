package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.dto.ExceptionResponse;
import com.waes.base64compare.domain.exception.ApiException;
import com.waes.base64compare.domain.exception.ArrayNotEqualException;
import com.waes.base64compare.domain.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Configurations to correct format response return of any exception in the rest context.
 */
@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * Handle and format any treated application exception.
     *
     * @param ex custom exception that inherit from Exception.
     * @return Http status 400 and a json of ExceptionResponse.
     */
    @ExceptionHandler(value = {
            ApiException.class,
            MethodArgumentNotValidException.class,
            DataBaseException.class,
            ArrayNotEqualException.class
    })
    protected ResponseEntity<ExceptionResponse> handleApiException(Exception ex) {

        LOGGER.error("Expected error: {}", ex.getMessage());

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle and format any not treated application exception.
     *
     * @param ex is any not treated exception.
     * @return Http status 500 and a json of ExceptionResponse.
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {

        String message = String.format("Not well treated exception: %s - Exception Type: %s", ex.getMessage(), ex.getClass());
        LOGGER.error("Expected error: {}", message);

        return new ResponseEntity<>(new ExceptionResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
