package com.waes.base64compare.domain.dto;

import com.waes.base64compare.domain.validator.Base64;

import javax.validation.constraints.NotEmpty;

/**
 * The object sent by diff controller to compare json base 694 data.
 */
public class JsonData {

    @NotEmpty
    @Base64
    private String base64;

    /**
     * Constructor default.
     */
    public JsonData() {
    }

    /**
     * Constructor expected.
     * @param base64 valid base 64 data.
     */
    public JsonData(@NotEmpty @Base64 String base64) {
        this.base64 = base64;
    }

    public String getBase64() {
        return base64;
    }
}
