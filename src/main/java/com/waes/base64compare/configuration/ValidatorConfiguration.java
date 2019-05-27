package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.dto.JsonData;
import com.waes.base64compare.domain.validator.DomainValidator;
import com.waes.base64compare.domain.validator.IValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration responsible for create the bean to validate javax.validation classes.
 */
@Configuration
public class ValidatorConfiguration {

    /**
     * Instance of some IValidator class with validate(T) method.
     * @return a instance of DomainValidator.
     */
    @Bean(name="JsonData")
    public IValidator<JsonData> getValidator(){
        return new DomainValidator<>();
    }
}
