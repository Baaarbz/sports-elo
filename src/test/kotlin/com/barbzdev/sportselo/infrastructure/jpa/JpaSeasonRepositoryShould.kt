package com.barbzdev.sportselo.infrastructure.jpa

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
    val seasonsInDatasource =
      mutableListOf(
        create(2000, "https://www.url.com/season/2000"),
        create(2001, "https://www.url.com/season/2001"),
        create(2002, "https://www.url.com/season/2002"),
        create(2003, "https://www.url.com/season/2003"),
        expectedSeason)
    seasonsInDatasource.shuffle()
    every { seasonDatasource.findAll() } returns seasonsInDatasource.map { seasonMapper.toEntity(it) }

    val response = jpaSeasonRepository.getLastSeasonLoaded()

    assertThat(response).isEqualTo(expectedSeason)
    verify { seasonDatasource.findAll() }
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
    every { seasonDatasource.findAll() } returns seasonsInDatasource.map { seasonMapper.toEntity(it) }

    val response = jpaSeasonRepository.getLastYearLoaded()

    assertThat(response).isEqualTo(SeasonYear(2004))
    verify { seasonDatasource.findAll() }
  }

  @Test
  fun `return null if there are no seasons year loaded`() {
    every { seasonDatasource.findAll() } returns emptyList()

    val response = jpaSeasonRepository.getLastYearLoaded()

    assertThat(response).isEqualTo(null)
  }
}
