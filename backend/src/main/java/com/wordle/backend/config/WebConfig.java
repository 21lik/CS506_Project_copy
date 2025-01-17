package com.wordle.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO: add documentation, this is to enable CORS (see <https://spring.io/guides/gs/rest-service-cors#global-cors-configuration>)

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:3000", "http://frontend:3000", "*"); // TODO: change when dockerize
            }
        };
    }
}
