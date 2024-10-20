package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.repository.F1Circuit
import com.barbzdev.f1elo.domain.repository.F1Constructor
import com.barbzdev.f1elo.domain.repository.F1Driver
import com.barbzdev.f1elo.domain.repository.F1Location
import com.barbzdev.f1elo.domain.repository.F1Race
import com.barbzdev.f1elo.domain.repository.F1Result
import com.barbzdev.f1elo.domain.repository.F1Time
import com.barbzdev.f1elo.factory.SeasonFactory
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfig
import com.barbzdev.f1elo.infrastructure.spring.repository.http.HttpJolpiF1Repository
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.apache.http.HttpHeaders.CONTENT_TYPE
import org.apache.http.HttpStatus.SC_OK
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class HttpJolpiF1RepositoryShould : IntegrationTestConfig() {

  @Autowired
  private lateinit var repository: HttpJolpiF1Repository

  @Test
  fun `gather races of a season`() {
    val aSeason = SeasonFactory.aSeason()
    givenFirstPageOfSeasonRaces(aSeason)
    givenSeconPageOfSeasonRaces(aSeason)

    val response = repository.gatherRacesBySeason(aSeason)
  }

  private fun givenFirstPageOfSeasonRaces(season: Season) = stubFor(
    get(urlEqualTo("/${season.year().value}/results/?limit=100&offset=0"))
      .willReturn(
        aResponse()
          .withStatus(SC_OK)
          .withHeader(CONTENT_TYPE, "application/json")
          .withBody(FIRST_PAGE_RACE_OF_SEASON_RESULTS)
      )
  )

  private fun givenFirstPageOfSeasonRaces(season: Season) = stubFor(
    get(urlEqualTo("/${season.year().value}/results/?limit=100&offset=100"))
      .willReturn(
        aResponse()
          .withStatus(SC_OK)
          .withHeader(CONTENT_TYPE, "application/json")
          .withBody(SECOND_PAGE_RACE_OF_SEASON_RESULTS)
      )
  )

  private companion object {
    @Language("JSON")
    const val FIRST_PAGE_RACE_OF_SEASON_RESULTS = """
      {
    "MRData": {
        "xmlns": "",
        "series": "f1",
        "url": "http://api.jolpi.ca/ergast/f1/1950/results/",
        "limit": "1",
        "offset": "0",
        "total": "160",
        "RaceTable": {
            "season": "1950",
            "Races": [
                {
                    "season": "1950",
                    "round": "1",
                    "url": "http://en.wikipedia.org/wiki/1950_British_Grand_Prix",
                    "raceName": "British Grand Prix",
                    "Circuit": {
                        "circuitId": "silverstone",
                        "url": "http://en.wikipedia.org/wiki/Silverstone_Circuit",
                        "circuitName": "Silverstone Circuit",
                        "Location": {
                            "lat": "52.0786",
                            "long": "-1.01694",
                            "locality": "Silverstone",
                            "country": "UK"
                        }
                    },
                    "date": "1950-05-13",
                    "Results": [
                        {
                            "number": "2",
                            "position": "1",
                            "positionText": "1",
                            "points": "9",
                            "Driver": {
                                "driverId": "farina",
                                "url": "http://en.wikipedia.org/wiki/Nino_Farina",
                                "givenName": "Nino",
                                "familyName": "Farina",
                                "dateOfBirth": "1906-10-30",
                                "nationality": "Italian"
                            },
                            "Constructor": {
                                "constructorId": "alfa",
                                "url": "http://en.wikipedia.org/wiki/Alfa_Romeo_in_Formula_One",
                                "name": "Alfa Romeo",
                                "nationality": "Swiss"
                            },
                            "grid": "1",
                            "laps": "70",
                            "status": "Finished",
                            "Time": {
                                "millis": "8003600",
                                "time": "2:13:23.600"
                            }
                        }
                    ]
                }
            ]
        }
    }
}
    """

    @Language("JSON")
    const val SECOND_PAGE_RACE_OF_SEASON_RESULTS = """
      {
    "MRData": {
        "xmlns": "",
        "series": "f1",
        "url": "http://api.jolpi.ca/ergast/f1/1950/results/",
        "limit": "1",
        "offset": "1",
        "total": "160",
        "RaceTable": {
            "season": "1950",
            "Races": [
                {
                    "season": "1950",
                    "round": "1",
                    "url": "http://en.wikipedia.org/wiki/1950_British_Grand_Prix",
                    "raceName": "British Grand Prix",
                    "Circuit": {
                        "circuitId": "silverstone",
                        "url": "http://en.wikipedia.org/wiki/Silverstone_Circuit",
                        "circuitName": "Silverstone Circuit",
                        "Location": {
                            "lat": "52.0786",
                            "long": "-1.01694",
                            "locality": "Silverstone",
                            "country": "UK"
                        }
                    },
                    "date": "1950-05-13",
                    "Results": [
                        {
                            "number": "3",
                            "position": "2",
                            "positionText": "2",
                            "points": "6",
                            "Driver": {
                                "driverId": "fagioli",
                                "url": "http://en.wikipedia.org/wiki/Luigi_Fagioli",
                                "givenName": "Luigi",
                                "familyName": "Fagioli",
                                "dateOfBirth": "1898-06-09",
                                "nationality": "Italian"
                            },
                            "Constructor": {
                                "constructorId": "alfa",
                                "url": "http://en.wikipedia.org/wiki/Alfa_Romeo_in_Formula_One",
                                "name": "Alfa Romeo",
                                "nationality": "Swiss"
                            },
                            "grid": "2",
                            "laps": "70",
                            "status": "Finished",
                            "Time": {
                                "millis": "8006200",
                                "time": "+2.600"
                            }
                        }
                    ]
                }
            ]
        }
    }
}
    """

    val EXPECTED_F1_RACES_RESPONSE = listOf(
      F1Race(
        season = 1950,
        round = 1,
        url = "http://en.wikipedia.org/wiki/1950_British_Grand_Prix",
        raceName = "British Grand Prix",
        circuit = F1Circuit(
          circuitId = "silverstone",
          url = "http://en.wikipedia.org/wiki/Silverstone_Circuit",
          circuitName = "Silverstone Circuit",
          location = F1Location("52.9786", "-1.01694", "Silverstone", "UK")
        ),
        date = "1950-05-13",
        results = listOf(
          F1Result(
            number = "2",
            position = "1",
            points = 9f,
            driver = F1Driver(
              driverId = "farina",
              url = "http://en.wikipedia.org/wiki/Nino_Farina",
              code = null,
              permanentNumber = null,
              givenName = "Nino",
              familyName = "Farina",
              dateOfBirth = "1906-10-30",
              nationality = "Italian",
            ),
            constructor = F1Constructor(
              constructorId = "alfa",
              url = "http://en.wikipedia.org/wiki/Alfa_Romeo_in_Formula_One",
              name = "Alfa Romeo",
              nationality = "Swiss"
            ),
            grid = 1,
            laps = 70,
            status = "Finished",
            time = F1Time(
              millis = 8003600,
              time = "2:13:23.600"
            ),
            fastestLap = null
          ),
          F1Result(
            number = "3",
            position = "2",
            points = 6f,
            driver = F1Driver(
              driverId = "fagioli",
              permanentNumber = null,
              code = null,
              url = "http://en.wikipedia.org/wiki/Luigi_Fagioli",
              givenName = "Luigi",
              familyName = "Fagioli",
              dateOfBirth = "1898-06-09",
              nationality = "Italian"
            ),
            constructor = F1Constructor(
              constructorId = "alfa",
              url = "http://en.wikipedia.org/wiki/Alfa_Romeo_in_Formula_One",
              name = "Alfa Romeo",
              nationality = "Swiss"
            ),
            grid = 2,
            laps = 70,
            status = "Finished",
            time = F1Time(
              millis = 8006200,
              time = "+2.600"
            ),
            fastestLap = null
          )
        ),
        time = null
      )
    )
  }
}
