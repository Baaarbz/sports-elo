package com.barbzdev.sportselo.formulaone.domain.valueobject.race

import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.Driver

data class RaceResult(
  val id: String,
  val number: String,
  val driver: Driver,
  val position: Int,
  val points: Float,
  val constructor: Constructor,
  val grid: Int,
  val laps: Int,
  val status: RaceResultStatus,
  val timeInMillis: Long?,
  val fastestLapInMillis: Long?,
  val averageSpeed: Float?,
  val averageSpeedUnit: String?,
)
