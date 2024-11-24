package com.barbzdev.f1elo.infrastructure.spring.documentation

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

  @Bean
  fun formula1EloAPI(): OpenAPI {
    val devServer = Server()
    devServer.url = "http://localhost:8000"
    devServer.description = "Server URL in development environment"

    val prodServer = Server()
    prodServer.url = "https://f1elo.barbzdev.com"
    prodServer.description = "Server URL in Production environment"

    val contact = Contact()
    contact.name = "Barbz"
    contact.url = "https://www.barbzdev.com"

    val mitLicense: License =
      License().name("MIT License with Commons Clause").url("https://github.com/Baaarbz/f1-elo/blob/main/LICENSE")

    val info: Info =
      Info()
        .title("Formula 1 ELO System API")
        .version("1.0")
        .contact(contact)
        .description(
          "This API will be responsible of calculating the ELO for the Formula 1. This is a non official API")
        .license(mitLicense)

    return OpenAPI().info(info).servers(listOf(devServer, prodServer))
  }
}
