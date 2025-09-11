package com.ecobazaar.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Test endpoint is working!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Health check passed!";
    }
}
