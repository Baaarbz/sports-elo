package com.barbzdev.sportselo.testcases

import com.barbzdev.sportselo.AcceptanceTestConfiguration
import com.barbzdev.sportselo.factory.DriverFactory.hamilton
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.HttpStatus

abstract class GetDriverByIdShould : AcceptanceTestConfiguration() {

  @Test
  fun `get driver by id`() {
    givenADriverInDatabase()

    val response = whenGetDriverByIdRequest()

    thenVerifyResponseIsExpected(response)
  }

  private fun givenADriverInDatabase() = driverRepository.save(A_DRIVER)

  private fun whenGetDriverByIdRequest() =
    given()
      .port(port.toInt())
      .contentType(ContentType.JSON)
      .`when`()
      .get("/api/v1/drivers/hamilton")
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
    val A_DRIVER = hamilton

    @Language("JSON")
    val EXPECTED_RESPONSE =
      """
      {
        "id": "hamilton",
        "fullName": {
          "givenName": "Lewis",
          "familyName": "Hamilton"
        } ,
        "code": "HAM",
        "permanentNumber": "44",
        "birthDate": "1985-01-07",
        "nationality": {
          "countryCode": "GB",
          "countryName": "United Kingdom",
          "value": "BRITISH"
        },
        "infoUrl": "https://en.wikipedia.org/wiki/Lewis_Hamilton",
        "currentElo": {
          "value": 2000,
          "occurredOn": "2023-04-30"
        },
        "highestElo": {
          "value": 2000,
          "occurredOn": "1900-01-01"
        },
        "lowestElo": {
          "value": 2000,
          "occurredOn": "1900-01-01"
        },
        "eloRecord": [
          {
            "value": 2000,
            "occurredOn": "1900-01-01"
          }
        ],
        "currentIRating": {
          "value": 2000,
          "occurredOn": "2023-04-30"
        },
        "highestIRating": {
          "value": 2300,
          "occurredOn": "2023-03-26"
        },
        "lowestIRating": {
          "value": 1900,
          "occurredOn": "2023-03-19"
        },
        "iratingRecord": [
          {
            "value": 2000,
            "occurredOn": "2023-03-05"
          },
          {
            "value": 1900,
            "occurredOn": "2023-03-19"
          },
          {
            "value": 2300,
            "occurredOn": "2023-03-26"
          },
          {
            "value": 2100,
            "occurredOn": "2023-04-30"
          }
        ]
      }
      """
  }
}
