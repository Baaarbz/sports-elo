package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.Circuit
import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.RaceResult
import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.repository.F1AverageSpeed
import com.barbzdev.f1elo.domain.repository.F1Circuit
import com.barbzdev.f1elo.domain.repository.F1Constructor
import com.barbzdev.f1elo.domain.repository.F1Driver
import com.barbzdev.f1elo.domain.repository.F1FastestLap
import com.barbzdev.f1elo.domain.repository.F1Location
import com.barbzdev.f1elo.domain.repository.F1Race
import com.barbzdev.f1elo.domain.repository.F1Result
import com.barbzdev.f1elo.domain.repository.F1Time

object RaceFactory {
  val races = listOf(
    Race.create(
      round = 1,
      name = "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix",
      occurredOn = "2023-03-05",
      infoUrl = "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix",
      circuit = CircuitFactory.aCircuit(),
    ).addResult(
      driver = DriverFactory.aDriver(),
      constructor = ConstructorFactory.aConstructor(),
      grid = 5,
      position = 1,
      points = 25f,
      laps = 57,
      averageSpeed = 245.3f,
      averageSpeedUnit = "kph",
      timeInMillis = 5400000,
      fastestLapInMillis = 90000,
      status = "Finished",
      number = "14"
    ).addResult(
      driver = DriverFactory.aDriver(),
      constructor = ConstructorFactory.aConstructor(),
      grid = 2,
      position = 2,
      points = 18f,
      laps = 57,
      averageSpeed = 244.3f,
      averageSpeedUnit = "kph",
      timeInMillis = 5400000,
      fastestLapInMillis = 91000,
      status = "Finished",
      number = "1"
    ),
    Race.create(
      round = 2,
      name = "https://en.wikipedia.org/wiki/2023_Saudi_Arabian_Grand_Prix",
      occurredOn = "2023-03-19",
      infoUrl = "https://en.wikipedia.org/wiki/2023_Saudi_Arabian_Grand_Prix",
      circuit = CircuitFactory.aCircuit(),
    ).addResult(
      driver = DriverFactory.aDriver(),
      constructor = ConstructorFactory.aConstructor(),
      grid = 1,
      position = 1,
      points = 25f,
      laps = 50,
      averageSpeed = 240.3f,
      averageSpeedUnit = "kph",
      timeInMillis = 4800000,
      fastestLapInMillis = 80000,
      status = "Finished",
      number = "7"
    ).addResult(
      driver = DriverFactory.aDriver(),
      constructor = ConstructorFactory.aConstructor(),
      grid = 3,
      position = 2,
      points = 18f,
      laps = 50,
      averageSpeed = 239.3f,
      averageSpeedUnit = "kph",
      timeInMillis = 4800000,
      fastestLapInMillis = 81000,
      status = "Finished",
      number = "44"
    ),
  )

  fun aRace() = races.random()

  fun aF1RacesFrom(aSeason: Season) = aSeason.races().map { it.toF1Race(aSeason.year().value) }

  private fun Race.toF1Race(season: Int) = F1Race(
    round = this.round().value,
    raceName = this.name().value,
    date = this.occurredOn().date,
    url = this.infoUrl().value,
    circuit = this.circuit().toF1Circuit(),
    results = this.results().map { it.toF1Result() },
    season = season,
    time = null
  )

  private fun Circuit.toF1Circuit() = F1Circuit(
    circuitId = this.id().value,
    circuitName = this.name().value,
    url = this.infoUrl().value,
    location = F1Location(
      country = this.country().value,
      locality = this.locality().value,
      lat = this.location().longitude,
      long = this.location().longitude
    )
  )

  private fun Constructor.toF1Constructor() = F1Constructor(
    constructorId = this.id().value,
    name = this.name().value,
    url = this.infoUrl().value,
    nationality = this.nationality().name,
  )

  private fun RaceResult.toF1Result() = F1Result(
    number = this.number,
    position = this.position.toString(),
    points = this.points,
    driver = this.driver.toF1Driver(),
    constructor = this.constructor.toF1Constructor(),
    grid = this.grid,
    laps = this.laps,
    status = this.status.name,
    time = F1Time(
      millis = this.timeInMillis,
      time = this.timeInMillis.toString(),
    ),
    fastestLap = F1FastestLap(
      rank = 1,
      lap = 1,
      time = F1Time(
        millis = this.fastestLapInMillis ?: 0,
        time = (this.fastestLapInMillis ?: 0).toString()
      ),
      averageSpeed = F1AverageSpeed(
        speed = this.averageSpeed ?: 0f,
        units = this.averageSpeedUnit ?: "kph"
      )
    )
  )

  private fun Driver.toF1Driver() = F1Driver(
    driverId = this.id().value,
    permanentNumber = this.permanentNumber()?.value,
    code = this.code()?.value,
    url = this.infoUrl().value,
    givenName = this.fullName().givenName,
    familyName = this.fullName().familyName,
    dateOfBirth = this.birthDate().date,
    nationality = this.nationality().name
  )

}
