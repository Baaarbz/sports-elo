package com.barbzdev.sportselo.core.infrastructure.framework.documentation

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

  @Bean
  fun sportsEloAPI(): OpenAPI {
    val devServer = Server()
    devServer.url = "http://localhost:8000"
    devServer.description = "Server URL in development environment"

    val prodServer = Server()
    prodServer.url = "https://sportselo.barbzdev.com"
    prodServer.description = "Server URL in Production environment"

    val contact = Contact()
    contact.name = "Barbz"
    contact.url = "https://barbzdev.com"

    val mitLicense: License =
      License().name("MIT License with Commons Clause").url("https://github.com/Baaarbz/sports-elo/blob/main/LICENSE")

    val info: Info =
      Info()
        .title("Sports ELO System API")
        .version("1.0")
        .contact(contact)
        .description(
          "This API will be responsible of calculating the ELO for different sports. This is a non official API")
        .license(mitLicense)

    val basicAuthScheme = SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")

    val securityRequirement = SecurityRequirement().addList("basicAuth")

    return OpenAPI()
      .info(info)
      .servers(listOf(devServer, prodServer))
      .components(Components().addSecuritySchemes("basicAuth", basicAuthScheme))
      .addSecurityItem(securityRequirement)
  }
}
