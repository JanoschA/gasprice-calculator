package com.dejaad.gpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("http://localhost:3000", "http://localhost:4200")
                .allowedOrigins("*") // TODO: change me!!!
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                /*.allowedHeaders(String.valueOf(Arrays.asList(
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ACCEPT,
                        HttpHeaders.X
                )))*/
                .allowedHeaders("*") // TODO: change me!!!
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
