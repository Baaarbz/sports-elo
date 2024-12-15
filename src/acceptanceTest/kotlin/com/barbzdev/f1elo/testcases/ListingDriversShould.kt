package com.barbzdev.f1elo.testcases

import com.barbzdev.f1elo.AcceptanceTestConfiguration
import com.barbzdev.f1elo.factory.SeasonFactory.seasonOf2014
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.HttpStatus

abstract class ListingDriversShould : AcceptanceTestConfiguration() {

  @Test
  fun `get listing of drivers paginated`() {
    givenASeasonInDatabase()

    val response = whenGetListingDriversRequest()

    thenVerifyResponseIsExpected(response)
  }

  private fun givenASeasonInDatabase() = seasonRepository.save(SEASON_IN_DATABASE)

  private fun whenGetListingDriversRequest() =
    given()
      .port(port.toInt())
      .contentType(ContentType.JSON)
      .`when`()
      .get("/api/v1/drivers")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())
      .extract()
      .body()
      .asString()

  private fun thenVerifyResponseIsExpected(response: String) {
    JSONAssert.assertEquals(EXPECTED_RESPONSE, response, JSONCompareMode.NON_EXTENSIBLE)
  }

  private companion object {
    val SEASON_IN_DATABASE = seasonOf2014

    @Language("JSON")
    const val EXPECTED_RESPONSE =
      """
      {
        "drivers": [
          {
            "id": "alonso",
            "fullName": {
              "givenName":"Fernando",
              "familyName": "Alonso"
            } ,
            "currentElo": 2000,
            "highestElo": 2000,
            "lowestElo": 2000,
            "currentIRating": 2000,
            "highestIRating": 2300,
            "lowestIRating": 1900,
            "lastRaceDate": "2023-04-30"
          },
          {
            "id": "hamilton",
            "fullName": {
              "givenName":"Lewis",
              "familyName": "Hamilton"
            } ,
            "currentElo": 2000,
            "highestElo": 2000,
            "lowestElo": 2000,
            "currentIRating": 2000,
            "highestIRating": 2300,
            "lowestIRating": 1900,
            "lastRaceDate": "2023-04-30"
          }
        ],
        "page": 0,
        "pageSize": 25,
        "totalElements": 2,
        "totalPages": 1
    }
    """
  }
}
