package com.barbzdev.f1elo.infrastructure.spring.repository.http

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.repository.F1AverageSpeed
import com.barbzdev.f1elo.domain.repository.F1Circuit
import com.barbzdev.f1elo.domain.repository.F1Constructor
import com.barbzdev.f1elo.domain.repository.F1Driver
import com.barbzdev.f1elo.domain.repository.F1FastestLap
import com.barbzdev.f1elo.domain.repository.F1Location
import com.barbzdev.f1elo.domain.repository.F1Race
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.F1Result
import com.barbzdev.f1elo.domain.repository.F1Season
import com.barbzdev.f1elo.domain.repository.F1Time
import com.barbzdev.f1elo.infrastructure.spring.configuration.JolpiF1Properties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.client.RestClient

class HttpJolpiF1Repository(
  private val restClient: RestClient,
  private val jolpiF1Properties: JolpiF1Properties
) : F1Repository {
  override fun gatherRacesBySeason(season: Season): List<F1Race> {
    val responses = mutableListOf<RaceResultResponse>()
    val limit = 100
    var offset = 0
    var total: Int

    do {
      val response = restClient
        .get()
        .uri(buildUriGetRacesBySeason(season, limit, offset))
        .retrieve()
        .toEntity(RaceResultResponse::class.java)

      total = response.body!!.mrData.total.toInt()
      offset += limit
      responses.add(response.body!!)
    } while (offset < total)


    return buildF1RaceResponse(responses)
  }

  override fun gatherAllSeasons(): List<F1Season> {
    TODO("Not yet implemented")
  }

  private fun buildUriGetRacesBySeason(season: Season, limit: Int, offset: Int) =
    "${jolpiF1Properties.baseUrl}/${season.year().value}/results/?limit=$limit&offset=$offset"

  private fun buildF1RaceResponse(responses: List<RaceResultResponse>): List<F1Race> {
    val raceMap = mutableMapOf<String, F1Race>()

    responses.forEach { response ->
      response.mrData.raceTable.races.forEach { jolpiRace ->
        val raceKey = "${jolpiRace.season}-${jolpiRace.round}"
        val existingRace = raceMap[raceKey]

        if (existingRace == null) {
          raceMap[raceKey] = jolpiRace.toF1Race()
        } else {
          raceMap[raceKey] = existingRace.copy(results = existingRace.results.plus(jolpiRace.results.map { it.toF1Result() }))
        }
      }
    }

    return raceMap.values.toList()
  }

  private fun JolpiRace.toF1Race() = F1Race(
    season = this.season.toInt(),
    round = this.round.toInt(),
    url = this.url,
    raceName = this.raceName,
    circuit = this.circuit.toF1Circuit(),
    date = this.date,
    time = this.time,
    results = this.results.map { it.toF1Result() }
  )

  private fun JolpiCircuit.toF1Circuit() = F1Circuit(
    circuitId = this.circuitId,
    url = this.url,
    circuitName = this.circuitName,
    location = this.location.toF1Location()
  )

  private fun JolpiLocation.toF1Location() = F1Location(
    lat = this.lat,
    long = this.long,
    locality = this.locality,
    country = this.country
  )

  private fun JolpiResult.toF1Result() = F1Result(
    number = this.number,
    position = this.position,
    points = this.points.toFloat(),
    driver = this.driver.toF1Driver(),
    constructor = this.constructor.toF1Constructor(),
    grid = this.grid.toInt(),
    laps = this.laps.toInt(),
    status = this.status,
    time = this.time.toF1Time(),
    fastestLap = this.fastestLap?.toF1FastestLap()
  )

  private fun JolpiDriver.toF1Driver() = F1Driver(
    driverId = this.driverId,
    permanentNumber = this.permanentNumber,
    code = this.code,
    url = this.url,
    givenName = this.givenName,
    familyName = this.familyName,
    dateOfBirth = this.dateOfBirth,
    nationality = this.nationality
  )

  private fun JolpiConstructor.toF1Constructor() = F1Constructor(
    constructorId = this.constructorId,
    url = this.url,
    name = this.name,
    nationality = this.nationality
  )

  private fun JolpiTime.toF1Time() = F1Time(
    millis = this.millis.toLong(),
    time = this.time
  )

  private fun JolpiFastestLap.toF1FastestLap() = F1FastestLap(
    rank = this.rank.toInt(),
    lap = this.lap.toInt(),
    time = this.time.toF1Time(),
    averageSpeed = this.averageSpeed.toF1AverageSpeed()
  )

  private fun JolpiAverageSpeed.toF1AverageSpeed() = F1AverageSpeed(
    units = this.units,
    speed = this.speed.toFloat()
  )
}

data class RaceResultResponse(
  @JsonProperty("MRData") val mrData: MRData
)

data class MRData(
  val total: String,
  @JsonProperty("RaceTable") val raceTable: RaceTable
)

data class RaceTable(
  @JsonProperty("Races") val races: List<JolpiRace>
)

data class JolpiRace(
  val season: String,
  val round: String,
  val url: String,
  val raceName: String,
  val circuit: JolpiCircuit,
  val date: String,
  val time: String,
  val results: List<JolpiResult>
)

data class JolpiCircuit(
  val circuitId: String,
  val url: String,
  val circuitName: String,
  val location: JolpiLocation
)

data class JolpiLocation(
  val lat: String,
  val long: String,
  val locality: String,
  val country: String
)

data class JolpiResult(
  val number: String,
  val position: String,
  val positionText: String,
  val points: String,
  val driver: JolpiDriver,
  val constructor: JolpiConstructor,
  val grid: String,
  val laps: String,
  val status: String,
  val time: JolpiTime,
  val fastestLap: JolpiFastestLap?
)

data class JolpiDriver(
  val driverId: String,
  val permanentNumber: String?,
  val code: String?,
  val url: String,
  val givenName: String,
  val familyName: String,
  val dateOfBirth: String,
  val nationality: String
)

data class JolpiConstructor(
  val constructorId: String,
  val url: String,
  val name: String,
  val nationality: String
)

data class JolpiTime(
  val millis: String,
  val time: String
)

data class JolpiFastestLap(
  val rank: String,
  val lap: String,
  val time: JolpiTime,
  val averageSpeed: JolpiAverageSpeed
)

data class JolpiAverageSpeed(
  val units: String,
  val speed: String
)
