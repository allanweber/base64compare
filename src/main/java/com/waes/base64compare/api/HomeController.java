package com.waes.base64compare.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Declares the base endpoint that simples redirect the request to the Swagger API documentation.
 */
@Controller
public class HomeController {

    /**
     * Endpoint that redirects the user to the Swagger API documentation.
     *
     * @return Spring redirect command to the swagger ui page.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:swagger-ui.html";
    }

}