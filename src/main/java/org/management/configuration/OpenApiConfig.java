package org.management.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // Creates and customizes the OpenAPI documentation for the Banking System Management Service.
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banking System Management Service Api Documentation")
                        .version("1.0.0")
                        .description("Banking System Management Service Api Documentation")

                );
    }
}