package com.barbzdev.sportselo.infrastructure.jpa

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.sportselo.domain.DriverId
import com.barbzdev.sportselo.factory.DriverFactory.aDriver
import com.barbzdev.sportselo.formulaone.infrastructure.jpa.JpaDriverRepository
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverEloHistoryDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverIRatingHistoryDatasource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import java.util.Optional
import org.junit.jupiter.api.Test

class JpaDriverRepositoryShould {
  private val driverDatasource: JpaDriverDatasource = mockk()
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource = mockk(relaxed = true)
  private val iRatingHistoryDatasource: JpaDriverIRatingHistoryDatasource = mockk(relaxed = true)

  private val jpaDriverRepository =
    JpaDriverRepository(driverDatasource, eloHistoryDatasource, iRatingHistoryDatasource)

  @Test
  fun `return a driver by id`() {
    val aDriver = aDriver()
    every { driverDatasource.findById(any()) } returns Optional.of(aDriver.toEntity())
    every { eloHistoryDatasource.findAllByDriver(any()) } returns aDriver.eloRecord().map { it.toEntity(aDriver) }
    every { iRatingHistoryDatasource.findAllByDriver(any()) } returns
      aDriver.iRatingRecord().map { it.toEntity(aDriver) }

    val response = jpaDriverRepository.findBy(aDriver.id())

    verifyOrder {
      driverDatasource.findById(aDriver.id().value)
      eloHistoryDatasource.findAllByDriver(aDriver.toEntity())
    }
    assertThat(response).isEqualTo(aDriver)
  }

  @Test
  fun `return null if driver does not exists`() {
    every { driverDatasource.findById(any()) } returns Optional.empty()

    val response = jpaDriverRepository.findBy(DriverId("non_existent"))

    verify(exactly = 1) { driverDatasource.findById("non_existent") }
    verify(exactly = 0) { eloHistoryDatasource.findAllByDriver(any()) }
    assertThat(response).isEqualTo(null)
  }
}
