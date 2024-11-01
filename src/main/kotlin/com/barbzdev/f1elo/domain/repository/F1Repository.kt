package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Season

interface F1Repository {
  fun gatherRacesBySeason(season: Season): List<F1Race>

  fun gatherAllSeasons(): List<F1Season>
}

data class F1Season(
  val season: Int,
  val url: String,
)

data class F1Race(
  val season: Int,
  val round: Int,
  val url: String,
  val raceName: String,
  val circuit: F1Circuit,
  val date: String,
  val time: String?,
  val results: List<F1Result>
)

data class F1Circuit(val circuitId: String, val url: String, val circuitName: String, val location: F1Location)

data class F1Location(val lat: String, val long: String, val locality: String, val country: String)

data class F1Result(
  val number: String,
  val position: String,
  val points: Float,
  val driver: F1Driver,
  val constructor: F1Constructor,
  val grid: Int,
  val laps: Int,
  val status: String,
  val time: F1Time?,
  val fastestLap: F1FastestLap?
)

data class F1Driver(
  val driverId: String,
  val permanentNumber: String?,
  val code: String?,
  val url: String,
  val givenName: String,
  val familyName: String,
  val dateOfBirth: String,
  val nationality: String
)

data class F1Constructor(val constructorId: String, val url: String, val name: String, val nationality: String)

data class F1Time(val millis: Long, val time: String)

data class F1FastestLap(val rank: Int, val lap: Int, val time: F1Time, val averageSpeed: F1AverageSpeed)

data class F1AverageSpeed(val units: String, val speed: Float)
