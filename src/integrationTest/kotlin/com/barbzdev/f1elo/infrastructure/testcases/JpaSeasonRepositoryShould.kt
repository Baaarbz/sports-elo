package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.jpa.JpaSeasonRepository
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.SeasonEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaSeasonRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaSeasonRepository

  @Autowired private lateinit var datasource: JpaSeasonDatasource

  @Test
  fun `save a season`() {
    val seasonToSave = aSeason()

    repository.save(seasonToSave)

    verifySeasonWasSaved(seasonToSave)
  }

  @Test
  fun `get the last season loaded`() {
    val seasonToSave = aSeason()
    repository.save(seasonToSave)

    val lastSeasonLoaded = repository.getLastSeasonLoaded()

    assertThat(lastSeasonLoaded).isEqualTo(seasonToSave)
  }

  private fun verifySeasonWasSaved(expectedSeasonEntitySaved: Season) {
    val actualSavedSeason = datasource.findAll()
    val expectedSeason =
      SeasonEntity(
        id = expectedSeasonEntitySaved.id().value,
        year = expectedSeasonEntitySaved.year().value,
        infoUrl = expectedSeasonEntitySaved.infoUrl().value)
    assertThat(actualSavedSeason).containsExactly(expectedSeason)
  }
}
