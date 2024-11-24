package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.jpa.JpaSeasonRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaSeasonRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaSeasonRepository

  @Autowired private lateinit var datasource: JpaSeasonDatasource

  @AfterEach
  fun cleanUp() {
    flyway.clean()
    flyway.migrate()
  }

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

  private fun givenASeasonInDatabase(): Season = aSeason().also { repository.save(it) }

  private fun verifySeasonWasSaved(expectedSeasonEntitySaved: Season) {
    val actualSavedSeason = datasource.findAll()
    val expectedSeason = expectedSeasonEntitySaved.toEntity()
    assertThat(actualSavedSeason).contains(expectedSeason)
  }
}
