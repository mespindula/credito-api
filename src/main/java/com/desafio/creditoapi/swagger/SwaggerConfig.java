package com.desafio.creditoapi.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API DE CONSULTA DE CRÉDITOS")
                        .description("API REST para consulta de créditos.")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .license(new License()
                                .name("Apache 2.0")
                        )
                ).externalDocs(
                        new ExternalDocumentation()
                                .description("Maicon Espindula"));
    }
}
