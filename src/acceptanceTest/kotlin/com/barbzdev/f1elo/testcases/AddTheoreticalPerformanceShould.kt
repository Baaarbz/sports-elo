package com.barbzdev.f1elo.testcases

import com.barbzdev.f1elo.AcceptanceTestConfiguration
import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.ConstructorPerformance
import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.factory.ConstructorFactory.aConstructor
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.infrastructure.jpa.JpaTheoreticalPerformanceRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.season.JpaSeasonDatasource
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class AddTheoreticalPerformanceShould : AcceptanceTestConfiguration() {
  @Autowired
  private lateinit var seasonDatasource: JpaSeasonDatasource

  @Autowired
  private lateinit var constructorDatasource: JpaConstructorDatasource

  @Autowired
  private lateinit var theoreticalPerformanceRepository: JpaTheoreticalPerformanceRepository

  @Test
  fun `add theoretical performance to the database`() {
    val aSeasonInDatabase = givenASeasonInDatabase()
    val aConstructorInDatabase = givenAConstructorInDatabase()

    whenAddTheoreticalPerformanceRequest(aSeasonInDatabase, aConstructorInDatabase)

    verifyTheoreticalPerformanceWasSaved(aSeasonInDatabase, aConstructorInDatabase)
  }

  private fun givenASeasonInDatabase(): Season = aSeason().also { seasonDatasource.save(it.toEntity()) }

  private fun givenAConstructorInDatabase(): Constructor = aConstructor().also { constructorDatasource.save(it.toEntity()) }

  private fun whenAddTheoreticalPerformanceRequest(season: Season, constructor: Constructor) {
    val request = """
      {
        "seasonYear": ${season.year().value},
        "isAnalyzedSeason": true,
        "constructorsPerformance": [
          {
            "constructorId": "${constructor.id().value}",
            "performance": 0.0
          }
        ]
      }
    """

    given()
      .contentType(ContentType.JSON)
      .auth()
      .basic("local", "local")
      .body(request)
      .post("/api/v1/theoretical-performance")
      .then()
      .statusCode(201)
  }

  private fun verifyTheoreticalPerformanceWasSaved(season: Season, constructor: Constructor) {
    val theoreticalPerformanceInDatabase = theoreticalPerformanceRepository.findBy(season.year())
    val expectedTheoreticalPerformance = TheoreticalPerformance.create(
      seasonYear = season.year().value,
      isAnalyzedSeason = true,
      constructorsPerformance = listOf(ConstructorPerformance(constructor, 0f))
    )
    assertThat(theoreticalPerformanceInDatabase).isEqualTo(expectedTheoreticalPerformance)
  }
}
