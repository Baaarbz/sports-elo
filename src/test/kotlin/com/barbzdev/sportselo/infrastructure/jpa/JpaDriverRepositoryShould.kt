package com.barbzdev.sportselo.infrastructure.jpa

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId
import com.barbzdev.sportselo.formulaone.factory.DriverFactory.aDriver
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.DriverMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.Optional
import org.junit.jupiter.api.Test

class JpaDriverRepositoryShould {
  private val driverDatasource: JpaDriverDatasource = mockk()
  private val driverMapper: DriverMapper = DriverMapper()

  private val jpaDriverRepository = JpaDriverRepository(driverDatasource, driverMapper)

  @Test
  fun `return a driver by id`() {
    val aDriver = aDriver()
    every { driverDatasource.findByIdJoinDriverEloHistory(any()) } returns Optional.of(driverMapper.toEntity(aDriver))

    val response = jpaDriverRepository.findBy(aDriver.id())

    verify { driverDatasource.findByIdJoinDriverEloHistory(aDriver.id().value) }
    assertThat(response).isEqualTo(aDriver)
  }

  @Test
  fun `return null if driver does not exists`() {
    every { driverDatasource.findByIdJoinDriverEloHistory(any()) } returns Optional.empty()

    val response = jpaDriverRepository.findBy(SportsmanId("non_existent"))

    verify(exactly = 1) { driverDatasource.findByIdJoinDriverEloHistory("non_existent") }
    assertThat(response).isEqualTo(null)
  }
}
