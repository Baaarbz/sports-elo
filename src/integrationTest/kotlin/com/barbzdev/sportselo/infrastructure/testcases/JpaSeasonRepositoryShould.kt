package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.SeasonMapper
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaSeasonRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaSeasonRepository

  @Autowired private lateinit var datasource: JpaSeasonDatasource

  @Autowired private lateinit var seasonMapper: SeasonMapper

  @Test
  fun `save a season`() {
    val seasonToSave = aSeason()

    repository.save(seasonToSave)

    verifySeasonWasSaved(seasonToSave)
  }

  @Test
  fun `get the last season loaded`() {
    val seasonInDatabase = givenASeasonInDatabase()

    val lastSeasonLoaded = repository.getLastSeasonLoaded()

    assertThat(lastSeasonLoaded).isEqualTo(seasonInDatabase)
  }

  @Test
  fun `get the last season year loaded`() {
    val seasonInDatabase = givenASeasonInDatabase()

    val lastSeasonLoaded = repository.getLastYearLoaded()

    assertThat(lastSeasonLoaded).isEqualTo(seasonInDatabase.year())
  }

  @Test
  fun `get the season id by year`() {
    val seasonInDatabase = givenASeasonInDatabase()

    val seasonId = repository.getSeasonIdBy(seasonInDatabase.year())

    assertThat(seasonId).isEqualTo(seasonInDatabase.id())
  }

  @Test
  fun `find all seasons years`() {
    val seasonInDatabase = givenASeasonInDatabase()

    val allSeasonsYears = repository.findAllSeasonsYears()

    assertThat(allSeasonsYears).contains(seasonInDatabase.year())
  }

  private fun givenASeasonInDatabase(): Season = aSeason().also { repository.save(it) }

  private fun verifySeasonWasSaved(expectedSeasonSaved: Season) {
    val seasonEntity =
      datasource.findByYearWithRaces(expectedSeasonSaved.year().value)
        ?: throw IllegalStateException("Season not found")
    val racesWithResults = datasource.findRaceResultsBySeasonId(seasonEntity.id)

    val raceMap = racesWithResults.associateBy { it.id }

    val updatedRaces = seasonEntity.races.map { raceMap[it.id] ?: it }
    val seasonWithResults = seasonEntity.copy(races = updatedRaces)

    val actualSeason = seasonMapper.toDomain(seasonWithResults)
    assertThat(actualSeason).isEqualTo(expectedSeasonSaved)
  }
}
