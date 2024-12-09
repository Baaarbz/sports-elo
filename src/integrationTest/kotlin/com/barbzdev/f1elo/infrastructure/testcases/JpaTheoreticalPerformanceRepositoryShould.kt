package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.ConstructorPerformance
import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.factory.ConstructorFactory.aConstructor
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.factory.TheoreticalPerformanceFactory.aTheoreticalPerformance
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.jpa.JpaTheoreticalPerformanceRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.season.JpaSeasonDatasource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaTheoreticalPerformanceRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaTheoreticalPerformanceRepository

  @Autowired private lateinit var constructorDatasource: JpaConstructorDatasource
  @Autowired private lateinit var seasonDatasource: JpaSeasonDatasource

  @Test
  fun `save a theoretical performance`() {
    val seasonInDatabase = givenASeasonInDatabase()
    val constructorInDatabase = givenAConstructorInDatabase()
    val aTheoreticalPerformance = aTheoreticalPerformance(seasonInDatabase.year().value, constructorInDatabase)

    repository.save(aTheoreticalPerformance)

    verifyTheoreticalPerformanceWasSaved(aTheoreticalPerformance)
  }

  @Test
  fun `get a theoretical performance by season year`() {
    val theoreticalPerformanceInDatabase = givenATheoreticalPerformanceInDatabase()

    val theoreticalPerformanceBySeasonYear = repository.findBy(theoreticalPerformanceInDatabase.seasonYear())

    assertThat(theoreticalPerformanceBySeasonYear).isEqualTo(theoreticalPerformanceInDatabase)
  }

  private fun verifyTheoreticalPerformanceWasSaved(expectedTheoreticalPerformance: TheoreticalPerformance) {
    val actualSavedTheoreticalPerformance = repository.findBy(expectedTheoreticalPerformance.seasonYear())
    assertThat(actualSavedTheoreticalPerformance).isEqualTo(expectedTheoreticalPerformance)
  }

  private fun givenATheoreticalPerformanceInDatabase(): TheoreticalPerformance {
    return TheoreticalPerformance.create(
        seasonYear = givenASeasonInDatabase().year().value,
        isAnalyzedSeason = true,
        constructorsPerformance = listOf(ConstructorPerformance(givenAConstructorInDatabase(), 0f)),
        dataOriginUrl = "https://x.com/DeltaData_",
        dataOriginSource = "DeltaData",
      )
      .also { repository.save(it) }
  }

  private fun givenASeasonInDatabase() = aSeason().also { seasonDatasource.save(it.toEntity()) }

  private fun givenAConstructorInDatabase() = aConstructor().also { constructorDatasource.save(it.toEntity()) }
}
