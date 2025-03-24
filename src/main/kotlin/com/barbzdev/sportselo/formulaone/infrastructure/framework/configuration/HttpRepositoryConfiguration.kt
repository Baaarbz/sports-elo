package com.barbzdev.sportselo.formulaone.infrastructure.framework.configuration

import com.barbzdev.sportselo.formulaone.domain.repository.F1Repository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.http.HttpJolpiF1Repository
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class HttpRepositoryConfiguration {

  @Bean fun restClient(): RestClient = RestClient.create()

  @Bean
  fun httpJolpiF1Repository(restClient: RestClient, jolpiF1Properties: JolpiF1Properties): F1Repository =
    HttpJolpiF1Repository(restClient = restClient, jolpiF1Properties = jolpiF1Properties)
}

@Configuration @ConfigurationProperties(prefix = "jolpi-f1") data class JolpiF1Properties(var baseUrl: String = "")
