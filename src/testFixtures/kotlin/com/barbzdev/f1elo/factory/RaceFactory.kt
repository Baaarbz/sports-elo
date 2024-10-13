package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.Race

object RaceFactory {
  private val races = listOf(
    Race.create(
      season = 2023,
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
      season = 2023,
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
}
