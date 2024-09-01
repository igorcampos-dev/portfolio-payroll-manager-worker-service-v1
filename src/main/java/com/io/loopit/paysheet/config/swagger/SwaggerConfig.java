package com.io.loopit.paysheet.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Api gerenciadora de contraCheques",
        description = "Esta api possui finalidade de gerenciar contraCheques internos da empresa",
        version = "1.0",
        contact = @Contact(
                name = "Igor de campos",
                email = "igorccampos8@gmail.com",
                url = "https://igorcampos-dev.github.io/"
        ),
        license = @License(
                name = "MIT License",
                url = "https://opensource.org/license/MIT"
        )
))
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SuppressWarnings("unused")
public class SwaggerConfig {}