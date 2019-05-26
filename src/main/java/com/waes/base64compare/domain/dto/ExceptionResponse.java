package com.waes.base64compare.domain.dto;

/**
 * The unique object returned in apis error cases.
 */
public class ExceptionResponse {

    private String message;

    /**
     * Principal constructor with message.
     * @param message the explicit error message.
     */
    public ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
