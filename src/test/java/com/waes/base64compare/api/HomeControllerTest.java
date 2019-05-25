package com.waes.base64compare.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class HomeControllerTest {

    @Test
    public void should_return_redirect_to_swagger_home() {
        String redirect = new HomeController().index();
        assertEquals("redirect:swagger-ui.html", redirect);
    }
}