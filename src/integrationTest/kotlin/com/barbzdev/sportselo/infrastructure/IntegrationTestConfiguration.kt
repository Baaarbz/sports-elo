package com.barbzdev.sportselo.infrastructure

import com.barbzdev.sportselo.infrastructure.spring.SpringApplication
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@AutoConfigureWireMock(port = 0)
class IntegrationTestConfiguration {
  @Autowired lateinit var flyway: Flyway

  @AfterEach
  fun cleanUp() {
    flyway.clean()
    flyway.migrate()
  }
}
