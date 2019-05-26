package com.waes.base64compare.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configure swagger to document rest apis.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    private static final String apiBasePackage = "com.waes.base64compare";

    /**
     * Creates the builder of the swagger interface application.
     * @return the builder.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(apiBasePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Creates the application info to show in swagger UI.
     * @return application info.
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Waes",
                "Compare two base64 json",
                "1.0",
                "Terms of service",
                new Contact("Allan Weber", "", "a.cassianoweber@gmail.com"),
                "", "", Collections.emptyList());
    }
}
