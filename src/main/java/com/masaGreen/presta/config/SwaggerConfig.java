package com.masaGreen.presta.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "Banking Management System",
                description = "OpenApi Docs for Banking Management System",
                termsOfService = "link to terms of service",
                contact = @Contact(email = "davidmachariamj@gmail.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Dev Env")
        }

)

@Configuration
class SwaggerConfig {

}
