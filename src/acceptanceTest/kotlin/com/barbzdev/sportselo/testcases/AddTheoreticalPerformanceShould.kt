package com.barbzdev.sportselo.testcases

import com.barbzdev.sportselo.AcceptanceTestConfiguration
import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.ConstructorPerformance
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.TheoreticalPerformance
import com.barbzdev.sportselo.factory.ConstructorFactory.aConstructor
import com.barbzdev.sportselo.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.formulaone.infrastructure.jpa.JpaTheoreticalPerformanceRepository
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonDatasource
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class AddTheoreticalPerformanceShould : AcceptanceTestConfiguration() {
  @Autowired private lateinit var seasonDatasource: JpaSeasonDatasource

  @Autowired private lateinit var constructorDatasource: JpaConstructorDatasource

  @Autowired private lateinit var theoreticalPerformanceRepository: JpaTheoreticalPerformanceRepository

  @Test
  fun `add theoretical performance to the database`() {
    val aSeasonInDatabase = givenASeasonInDatabase()
    val aConstructorInDatabase = givenAConstructorInDatabase()

    whenAddTheoreticalPerformanceRequest(aSeasonInDatabase, aConstructorInDatabase)

    verifyTheoreticalPerformanceWasSaved(aSeasonInDatabase, aConstructorInDatabase)
  }

  private fun givenASeasonInDatabase(): Season = aSeason().also { seasonDatasource.save(it.toEntity()) }

  private fun givenAConstructorInDatabase(): Constructor =
    aConstructor().also { constructorDatasource.save(it.toEntity()) }

  private fun whenAddTheoreticalPerformanceRequest(season: Season, constructor: Constructor) {
    val request =
      """
      {
        "seasonYear": ${season.year().value},
        "isAnalyzedData": true,
        "dataOrigin": {
          "source": "DeltaData",
          "url": "https://x.com/DeltaData_"
        },
        "theoreticalConstructorPerformances": [
          {
            "constructorId": "${constructor.id().value}",
            "performance": 0.0
          }
        ]
      }
    """

    given()
      .port(port.toInt())
      .contentType(ContentType.JSON)
      .auth()
      .basic("local", "local")
      .body(request)
      .`when`()
      .post("/api/v1/theoretical-performance")
      .then()
      .statusCode(201)
  }

  private fun verifyTheoreticalPerformanceWasSaved(season: Season, constructor: Constructor) {
    val theoreticalPerformanceInDatabase = theoreticalPerformanceRepository.findBy(season.year())
    val expectedTheoreticalPerformance =
      TheoreticalPerformance.create(
        seasonYear = season.year().value,
        isAnalyzedSeason = true,
        constructorsPerformance = listOf(ConstructorPerformance(constructor, 0f)),
        dataOriginSource = "DeltaData",
        dataOriginUrl = "https://x.com/DeltaData_")
    assertThat(theoreticalPerformanceInDatabase).isEqualTo(expectedTheoreticalPerformance)
  }
}
