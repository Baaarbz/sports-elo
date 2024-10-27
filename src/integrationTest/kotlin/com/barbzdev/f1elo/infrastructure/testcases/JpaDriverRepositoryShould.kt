package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.factory.DriverFactory.aDriver
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.jpa.JpaDriverRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverDatasource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaDriverRepositoryShould : IntegrationTestConfiguration() {
  @Autowired
  private lateinit var repository: JpaDriverRepository

  @Autowired
  private lateinit var datasource: JpaDriverDatasource

  @Test
  fun `save a driver`() {
    val aDriverToSave = aDriver()

    repository.save(aDriverToSave)

    verifyDriverWasSaved(aDriverToSave)
  }

  @Test
  fun `get a driver by id`() {
    val driverInDatabase = givenADriverInDatabase()

    val driverById = repository.findBy(driverInDatabase.id())

    assertThat(driverById).isEqualTo(driverInDatabase)
  }

  private fun givenADriverInDatabase(): Driver = aDriver().also { repository.save(it) }

  private fun verifyDriverWasSaved(expectedDriverEntitySaved: Driver) {
    val actualSavedDrivers = datasource.findAll()
    val expectedDriver = expectedDriverEntitySaved.toEntity()
    assertThat(actualSavedDrivers).containsExactly(expectedDriver)
  }
}
