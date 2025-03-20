package com.barbzdev.sportselo.factory

import com.barbzdev.sportselo.domain.Circuit
import com.barbzdev.sportselo.domain.Constructor
import com.barbzdev.sportselo.domain.Driver
import com.barbzdev.sportselo.domain.Race
import com.barbzdev.sportselo.domain.RaceResult
import com.barbzdev.sportselo.domain.Season
import com.barbzdev.sportselo.domain.repository.F1AverageSpeed
import com.barbzdev.sportselo.domain.repository.F1Circuit
import com.barbzdev.sportselo.domain.repository.F1Constructor
import com.barbzdev.sportselo.domain.repository.F1Driver
import com.barbzdev.sportselo.domain.repository.F1FastestLap
import com.barbzdev.sportselo.domain.repository.F1Location
import com.barbzdev.sportselo.domain.repository.F1Race
import com.barbzdev.sportselo.domain.repository.F1Result
import com.barbzdev.sportselo.domain.repository.F1Time
import com.barbzdev.sportselo.factory.CircuitFactory.interlagos
import com.barbzdev.sportselo.factory.ConstructorFactory.ferrariConstructor
import com.barbzdev.sportselo.factory.DriverFactory.alonso
import com.barbzdev.sportselo.factory.DriverFactory.hamilton

object RaceFactory {
  val races =
    listOf(
      Race.create(
          round = 1,
          name = "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix",
          occurredOn = "2023-03-05",
          infoUrl = "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix",
          circuit = CircuitFactory.aCircuit(),
        )
        .addResult(
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
          number = "14")
        .addResult(
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
          number = "1"),
      Race.create(
          round = 2,
          name = "https://en.wikipedia.org/wiki/2023_Saudi_Arabian_Grand_Prix",
          occurredOn = "2023-03-19",
          infoUrl = "https://en.wikipedia.org/wiki/2023_Saudi_Arabian_Grand_Prix",
          circuit = CircuitFactory.aCircuit(),
        )
        .addResult(
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
          number = "7")
        .addResult(
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
          number = "44"),
    )

  val raceOf2014 =
    Race.create(
        round = 1,
        name = "Interlagos Grand Prix",
        occurredOn = "2014-03-05",
        infoUrl = "https://en.wikipedia.org/wiki/2014_Brazilian_Grand_Prix",
        circuit = interlagos,
      )
      .addResult(
        driver = alonso,
        constructor = ferrariConstructor,
        grid = 5,
        position = 1,
        points = 25f,
        laps = 57,
        averageSpeed = 245.3f,
        averageSpeedUnit = "kph",
        timeInMillis = 5400000,
        fastestLapInMillis = 90000,
        status = "Finished",
        number = "14")
      .addResult(
        driver = hamilton,
        constructor = ferrariConstructor,
        grid = 2,
        position = 2,
        points = 18f,
        laps = 57,
        averageSpeed = 244.3f,
        averageSpeedUnit = "kph",
        timeInMillis = 5400000,
        fastestLapInMillis = 91000,
        status = "Finished",
        number = "1")

  fun aF1RacesFrom(aSeason: Season) = aSeason.races().map { it.toF1Race(aSeason.year().value) }

  private fun Race.toF1Race(season: Int) =
    F1Race(
      round = this.round().value,
      raceName = this.name().value,
      date = this.occurredOn().date,
      url = this.infoUrl().value,
      circuit = this.circuit().toF1Circuit(),
      results = this.results().map { it.toF1Result() },
      season = season,
      time = null)

  private fun Circuit.toF1Circuit() =
    F1Circuit(
      circuitId = this.id().value,
      circuitName = this.name().value,
      url = this.infoUrl().value,
      location =
        F1Location(
          country = this.country().value,
          locality = this.locality().value,
          lat = this.location().longitude,
          long = this.location().longitude))

  private fun Constructor.toF1Constructor() =
    F1Constructor(
      constructorId = this.id().value,
      name = this.name().value,
      url = this.infoUrl().value,
      nationality = this.nationality().name,
    )

  private fun RaceResult.toF1Result() =
    F1Result(
      number = this.number,
      position = this.position.toString(),
      points = this.points,
      driver = this.driver.toF1Driver(),
      constructor = this.constructor.toF1Constructor(),
      grid = this.grid,
      laps = this.laps,
      status = this.status.name,
      time =
        this.timeInMillis?.let {
          F1Time(
            millis = it,
            time = this.timeInMillis.toString(),
          )
        },
      fastestLap =
        F1FastestLap(
          rank = 1,
          lap = 1,
          time = F1Time(millis = this.fastestLapInMillis ?: 0, time = (this.fastestLapInMillis ?: 0).toString()),
          averageSpeed = F1AverageSpeed(speed = this.averageSpeed ?: 0f, units = this.averageSpeedUnit ?: "kph")))

  private fun Driver.toF1Driver() =
    F1Driver(
      driverId = this.id().value,
      permanentNumber = this.permanentNumber()?.value,
      code = this.code()?.value,
      url = this.infoUrl().value,
      givenName = this.fullName().givenName,
      familyName = this.fullName().familyName,
      dateOfBirth = this.birthDate().value,
      nationality = this.nationality().name)
}
