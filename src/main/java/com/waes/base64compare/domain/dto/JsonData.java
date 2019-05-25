package com.waes.base64compare.domain.dto;

import javax.validation.constraints.NotEmpty;

public class JsonData {

    @NotEmpty
    private String base64;

    public JsonData() {
    }

    public JsonData(@NotEmpty String base64) {
        this.base64 = base64;
    }

    public String getBase64() {
        return base64;
    }
}
