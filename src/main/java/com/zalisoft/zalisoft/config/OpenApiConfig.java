package com.zalisoft.zalisoft.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(contact= @Contact(
                name = "By USMAN KHALID", email = "loftyusman@gmail.com"
        ),
                description = "School-Api",
                title = "backendApi",
                version = "1.0"
        ),
        servers = {@Server(description = "LOCAL", url = "http://localhost:8080"),
                @Server(description = "PRODUCTION", url = "http://localhost:8080"),

        },
        security = {
                @SecurityRequirement(name = "bearerAuth",scopes = "LOCAL"),

        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "jwt auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
