package com.barbzdev.f1elo.infrastructure.jpa

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.f1elo.domain.Season.Companion.create
import com.barbzdev.f1elo.domain.SeasonYear
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaCircuitDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaConstructorDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverEloHistoryDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaRaceDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaRaceResultDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test

class JpaSeasonRepositoryShould {
  private val seasonDatasource: JpaSeasonDatasource = mockk()
  private val raceDatasource: JpaRaceDatasource = mockk()
  private val driverDatasource: JpaDriverDatasource = mockk(relaxed = true)
  private val constructorDatasource: JpaConstructorDatasource = mockk(relaxed = true)
  private val circuitDatasource: JpaCircuitDatasource = mockk(relaxed = true)
  private val raceResultDatasource: JpaRaceResultDatasource = mockk(relaxed = true)
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource = mockk(relaxed = true)

  private val jpaSeasonRepository =
    JpaSeasonRepository(
      seasonDatasource,
      raceDatasource,
      driverDatasource,
      constructorDatasource,
      circuitDatasource,
      raceResultDatasource,
      eloHistoryDatasource)

  @Test
  fun `return the last season loaded`() {
    val expectedSeason = create(2004, "https://www.url.com/season/2004")
    val seasonsInDatasource =
      mutableListOf(
        create(2000, "https://www.url.com/season/2000"),
        create(2001, "https://www.url.com/season/2001"),
        create(2002, "https://www.url.com/season/2002"),
        create(2003, "https://www.url.com/season/2003"),
        expectedSeason)
    seasonsInDatasource.shuffle()
    every { seasonDatasource.findAll() } returns seasonsInDatasource.map { it.toEntity() }
    every { raceDatasource.findAllBySeason(any()) } returns emptyList()

    val response = jpaSeasonRepository.getLastSeasonLoaded()

    assertThat(response).isEqualTo(expectedSeason)
    verifyOrder {
      seasonDatasource.findAll()
      raceDatasource.findAllBySeason(expectedSeason.toEntity())
    }
  }

  @Test
  fun `return null if there are no seasons loaded`() {
    every { seasonDatasource.findAll() } returns emptyList()

    val response = jpaSeasonRepository.getLastSeasonLoaded()

    assertThat(response).isEqualTo(null)
  }

  @Test
  fun `return the last year loaded`() {
    val expectedSeason = create(2004, "https://www.url.com/season/2004")
    val seasonsInDatasource =
      mutableListOf(
        create(2000, "https://www.url.com/season/2000"),
        create(2001, "https://www.url.com/season/2001"),
        create(2002, "https://www.url.com/season/2002"),
        create(2003, "https://www.url.com/season/2003"),
        expectedSeason)
    seasonsInDatasource.shuffle()
    every { seasonDatasource.findAll() } returns seasonsInDatasource.map { it.toEntity() }

    val response = jpaSeasonRepository.getLastYearLoaded()

    assertThat(response).isEqualTo(SeasonYear(2004))
    verifyOrder { seasonDatasource.findAll() }
  }

  @Test
  fun `return null if there are no seasons year loaded`() {
    every { seasonDatasource.findAll() } returns emptyList()

    val response = jpaSeasonRepository.getLastYearLoaded()

    assertThat(response).isEqualTo(null)
  }
}
