package com.waes.base64compare.configuration;

import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.infrastructure.repository.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure database instances.
 */
@Configuration
public class DatabaseConfiguration {

    /**
     * Create an instance of Database<DiffEntity>
     * @return an instance of Database<DiffEntity>
     */
    @Bean(name = "DiffDatabase")
    public Database getDiffDatabase(){
        return new Database<DiffEntity>();
    }
}
