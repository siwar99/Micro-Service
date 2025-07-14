package com.healthcare.shared.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-gateway", url = "http://localhost:8084")
public interface MedecinClient {
    
    @GetMapping("/api/medecins/{id}")
    MedecinResponse getMedecinById(@PathVariable("id") String id);
} 