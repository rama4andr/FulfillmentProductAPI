package com.github.rama4andr.fulfillmentproductapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Fulfillment Product API")
                        .version("0.0.1")
                        .description("API for managing products in fulfillment centers and inventory status data"));

    }
}
