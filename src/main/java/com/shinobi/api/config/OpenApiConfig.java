package com.shinobi.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Exchange Rate API")
                        .description("An API that can query data from the Narodowy Bank Polski's public API.\n")
                        .version("v1.0"));
    }
}
