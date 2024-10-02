package com.datajoy.web_builder.config;

import com.datajoy.web_builder.persistence.json.JsonFilePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonFilePersistenceConfig {
    @Bean
    public JsonFilePersistence jsonFilePersistence() {
        return new JsonFilePersistence("C:\\web-builder\\data");
    }
}
