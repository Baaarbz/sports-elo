package com.barbzdev.f1elo.infrastructure.com.barbzdev.f1elo

import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.infrastructure.spring.SpringApplication
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acceptance-test")
@AutoConfigureWireMock(port = 0)
class AcceptanceTestConfiguration {

  @Autowired private lateinit var flyway: Flyway

  @Autowired lateinit var seasonRepository: SeasonRepository

  @Autowired lateinit var driverRepository: DriverRepository

  @AfterEach
  fun cleanUp() {
    flyway.clean()
    flyway.migrate()
  }
}
