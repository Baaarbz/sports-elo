package com.barbzdev.sportselo.formulaone.infrastructure.jpa

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.sportselo.formulaone.domain.Season.Companion.create
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.CircuitMapper
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.ConstructorMapper
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.DriverMapper
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.SeasonMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class JpaSeasonRepositoryShould {
  private val seasonDatasource: JpaSeasonDatasource = mockk()
  private val seasonMapper: SeasonMapper = SeasonMapper(CircuitMapper(), DriverMapper(), ConstructorMapper())

  private val jpaSeasonRepository = JpaSeasonRepository(seasonDatasource, seasonMapper)

  @Test
  fun `return the last season loaded`() {
    val expectedSeason = create(2004, "https://www.url.com/season/2004")
    val expectedSeasonAsEntity = seasonMapper.toEntity(expectedSeason)
    every { seasonDatasource.findLastSeasonWithRaces() } returns expectedSeasonAsEntity
    every { seasonDatasource.findRaceResultsBySeasonId(any()) } returns expectedSeasonAsEntity.races

    val response = jpaSeasonRepository.getLastSeasonLoaded()

    assertThat(response).isEqualTo(expectedSeason)
    verify { seasonDatasource.findLastSeasonWithRaces() }
    verify { seasonDatasource.findRaceResultsBySeasonId(expectedSeason.id().value) }
  }

  @Test
  fun `return null if there are no seasons loaded`() {
    every { seasonDatasource.findLastSeasonWithRaces() } returns null

    val response = jpaSeasonRepository.getLastSeasonLoaded()

    assertThat(response).isEqualTo(null)
  }

  @Test
  fun `return the last year loaded`() {
    val expectedSeason = create(2004, "https://www.url.com/season/2004")
    val expectedSeasonAsEntity = seasonMapper.toEntity(expectedSeason)
    every { seasonDatasource.findLastSeasonWithRaces() } returns expectedSeasonAsEntity

    val response = jpaSeasonRepository.getLastYearLoaded()

    assertThat(response).isEqualTo(SeasonYear(2004))
    verify { seasonDatasource.findLastSeasonWithRaces() }
  }

  @Test
  fun `return null if there are no seasons year loaded`() {
    every { seasonDatasource.findLastSeasonWithRaces() } returns null

    val response = jpaSeasonRepository.getLastYearLoaded()

    assertThat(response).isEqualTo(null)
  }
}
