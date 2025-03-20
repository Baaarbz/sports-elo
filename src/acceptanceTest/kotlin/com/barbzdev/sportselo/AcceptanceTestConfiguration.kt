package com.barbzdev.sportselo

import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.core.infrastructure.framework.SpringApplication
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acceptance-test")
@AutoConfigureWireMock(port = 0)
class AcceptanceTestConfiguration {

  @LocalServerPort lateinit var port: String

  @Autowired private lateinit var flyway: Flyway

  @Autowired lateinit var seasonRepository: SeasonRepository

  @Autowired lateinit var driverRepository: DriverRepository

  @AfterEach
  fun cleanUp() {
    flyway.clean()
    flyway.migrate()
  }
}
