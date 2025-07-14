package com.healthcare.visite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig {

    @Value("${medecin.service.url}")
    private String medecinServiceUrl;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.target(medecinServiceUrl);
    }
} 