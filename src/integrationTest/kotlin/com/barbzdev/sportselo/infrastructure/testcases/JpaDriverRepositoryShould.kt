package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.core.domain.util.DomainPaginated
import com.barbzdev.sportselo.core.domain.util.Page
import com.barbzdev.sportselo.core.domain.util.PageSize
import com.barbzdev.sportselo.core.domain.util.SortBy
import com.barbzdev.sportselo.core.domain.util.SortOrder
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.factory.DriverFactory.aDriver
import com.barbzdev.sportselo.formulaone.factory.DriverFactory.hamilton
import com.barbzdev.sportselo.formulaone.factory.DriverFactory.verstappen
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.DriverMapper
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaDriverRepositoryShould : IntegrationTestConfiguration() {
  @Autowired
  private lateinit var repository: JpaDriverRepository

  @Autowired
  private lateinit var datasource: JpaDriverDatasource

  @Autowired
  private lateinit var driverMapper: DriverMapper

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
        totalPages = drivers.totalPages
      )
    assertThat(drivers).isEqualTo(expected)
  }

  @Test
  fun `find all drivers`() {
    givenADriverInDatabase(hamilton)
    givenADriverInDatabase(verstappen)

    val drivers = repository.findAll()

    assertThat(drivers).containsExactlyInAnyOrder(hamilton, verstappen)
  }

  private fun givenADriverInDatabase(): Driver = aDriver().also { repository.save(it) }

  private fun givenADriverInDatabase(driver: Driver) = repository.save(driver)

  private fun verifyDriverWasSaved(expectedDriverEntitySaved: Driver) {
    val actualSavedDrivers = datasource.findAll()
    val expectedDriver = driverMapper.toEntity(expectedDriverEntitySaved)
    assertThat(actualSavedDrivers).contains(expectedDriver)
  }
}
