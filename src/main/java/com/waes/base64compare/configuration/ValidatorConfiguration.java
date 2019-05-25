package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.validator.DomainValidator;
import com.waes.base64compare.domain.validator.IValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public IValidator getValidator(){
        return new DomainValidator();
    }
}
