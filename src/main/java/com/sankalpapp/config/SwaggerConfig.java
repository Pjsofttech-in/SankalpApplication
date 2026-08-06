package com.sankalpapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("GK Exam Management System API")
                        .version("1.0")
                        .description("REST APIs for GK Exam Management System")
                        .contact(new Contact()
                                .name("GK Team")
                                .email("support@gkexam.com")));
    }
}