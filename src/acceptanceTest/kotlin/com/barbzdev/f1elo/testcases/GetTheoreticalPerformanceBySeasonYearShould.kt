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
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired

abstract class GetTheoreticalPerformanceBySeasonYearShould : AcceptanceTestConfiguration() {
  @Autowired private lateinit var seasonDatasource: JpaSeasonDatasource

  @Autowired private lateinit var constructorDatasource: JpaConstructorDatasource

  @Autowired private lateinit var theoreticalPerformanceRepository: JpaTheoreticalPerformanceRepository

  @Test
  fun `add theoretical performance to the database`() {
    val aSeasonInDatabase = givenASeasonInDatabase()
    val aConstructorInDatabase = givenAConstructorInDatabase()
    val aTheoreticalPerformanceInDatabase =
      givenATheoreticalPerformanceInDatabase(aSeasonInDatabase, aConstructorInDatabase)

    val response = whenGetTheoreticalPerformanceRequest(aSeasonInDatabase)

    verifyGetTheoreticalPerformanceResponse(response, aTheoreticalPerformanceInDatabase)
  }

  private fun givenASeasonInDatabase(): Season = aSeason().also { seasonDatasource.save(it.toEntity()) }

  private fun givenAConstructorInDatabase(): Constructor =
    aConstructor().also { constructorDatasource.save(it.toEntity()) }

  private fun givenATheoreticalPerformanceInDatabase(
    aSeason: Season,
    aConstructor: Constructor
  ): TheoreticalPerformance {
    return TheoreticalPerformance.create(
        seasonYear = aSeason.year().value,
        isAnalyzedSeason = true,
        constructorsPerformance = listOf(ConstructorPerformance(aConstructor, 0f)),
        dataOriginUrl = "https://x.com/DeltaData_",
        dataOriginSource = "DeltaData",
      )
      .also { theoreticalPerformanceRepository.save(it) }
  }

  private fun whenGetTheoreticalPerformanceRequest(season: Season) =
    given()
      .port(port.toInt())
      .contentType(ContentType.JSON)
      .`when`()
      .get("/api/v1/theoretical-performance/${season.year().value}")
      .then()
      .statusCode(200)
      .extract()
      .body()
      .asString()

  private fun verifyGetTheoreticalPerformanceResponse(
    response: String,
    theoreticalPerformance: TheoreticalPerformance
  ) {
    val expectedResponse =
      """
      {
        "seasonYear": ${theoreticalPerformance.seasonYear().value},
        "isAnalyzedData": ${theoreticalPerformance.isAnalyzedSeason()},
        "dataOrigin": {
          "url": "${theoreticalPerformance.dataOrigin()!!.url}",
          "source": "${theoreticalPerformance.dataOrigin()!!.source}"
        },
        "theoreticalConstructorPerformances": [
          {
            "constructorId": "${theoreticalPerformance.constructorsPerformance()[0].constructor.id().value}",
            "performance": ${theoreticalPerformance.constructorsPerformance()[0].performance}
          }
        ]
      }"""
        .trimIndent()

    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE)
  }
}
