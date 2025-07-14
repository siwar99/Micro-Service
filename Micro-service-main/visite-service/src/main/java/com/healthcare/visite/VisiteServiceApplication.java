package com.healthcare.visite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.healthcare.shared.feign")
public class VisiteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisiteServiceApplication.class, args);
    }
} 