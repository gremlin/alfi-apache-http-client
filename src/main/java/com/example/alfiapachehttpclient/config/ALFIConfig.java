package com.example.alfiapachehttpclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gremlin.*;

@Configuration
public class ALFIConfig {

    private static final String APPLICATION_QUERY_NAME = "ALFIApacheHttpClientDemo";

    public GremlinCoordinatesProvider gremlinCoordinatesProvider() {
        return new GremlinCoordinatesProvider() {
            @Override
            public ApplicationCoordinates initializeApplicationCoordinates() {
                return new ApplicationCoordinates.Builder()
                        .withType(APPLICATION_QUERY_NAME)
                        .build();
            }
        };
    }

    public GremlinServiceFactory gremlinServiceFactory() {
        return new GremlinServiceFactory(gremlinCoordinatesProvider());
    }

    @Bean
    public GremlinService gremlinService() {
        return gremlinServiceFactory().getGremlinService();
    }

}
