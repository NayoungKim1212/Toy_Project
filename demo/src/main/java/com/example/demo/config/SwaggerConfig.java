package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "Toy Project API",
                description = "Toy Project API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {
}
