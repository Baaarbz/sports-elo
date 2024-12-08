package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.common.DomainPaginated
import com.barbzdev.f1elo.domain.common.Page
import com.barbzdev.f1elo.domain.common.PageSize
import com.barbzdev.f1elo.domain.common.SortBy
import com.barbzdev.f1elo.domain.common.SortOrder
import com.barbzdev.f1elo.factory.DriverFactory.aDriver
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.jpa.JpaDriverRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.driver.JpaDriverDatasource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaDriverRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaDriverRepository

  @Autowired private lateinit var datasource: JpaDriverDatasource

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

  @Test
  fun `get all drivers`() {
    val driverInDatabase = givenADriverInDatabase()

    val drivers = repository.findAll(Page(0), PageSize(1), SortBy("id"), SortOrder("asc"))

    val expected =
      DomainPaginated(
        elements = listOf(driverInDatabase),
        page = 0,
        pageSize = 1,
        totalElements = drivers.totalElements,
        totalPages = drivers.totalPages)
    assertThat(drivers).isEqualTo(expected)
  }

  private fun givenADriverInDatabase(): Driver = aDriver().also { repository.save(it) }

  private fun verifyDriverWasSaved(expectedDriverEntitySaved: Driver) {
    val actualSavedDrivers = datasource.findAll()
    val expectedDriver = expectedDriverEntitySaved.toEntity()
    assertThat(actualSavedDrivers).contains(expectedDriver)
  }
}
