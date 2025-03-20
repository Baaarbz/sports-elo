package com.barbzdev.sportselo.domain.service

import com.barbzdev.sportselo.domain.Driver
import com.barbzdev.sportselo.domain.RaceDate
import com.barbzdev.sportselo.domain.service.RaceResult.DRAW
import com.barbzdev.sportselo.domain.service.RaceResult.LOSE
import com.barbzdev.sportselo.domain.service.RaceResult.WIN
import kotlin.math.pow
import kotlin.math.round

class EloCalculator {

  fun calculateEloRatingsByPosition(driversByPosition: Map<Int, List<Driver>>, raceDate: RaceDate): List<Driver> {
    val mapOfDrivers = driversByPosition.values.flatten().associateBy { it.id() }.toMutableMap()

    for ((position, drivers) in driversByPosition) {
      val averageElo = drivers.getAverageElo()
      var accumulatedEloDeltaForPosition = 0

      for ((opponentPosition, opponents) in driversByPosition) {
        if (position != opponentPosition) {
          val averageOpponentElo = opponents.getAverageElo()
          val raceResult = if (position < opponentPosition) WIN else LOSE
          val kReducer = if (driversByPosition.size == 2) 1 else driversByPosition.size
          accumulatedEloDeltaForPosition += calculateEloDelta(averageElo, averageOpponentElo, raceResult, kReducer)
        }
      }

      val eloDeltaPerDriver = accumulatedEloDeltaForPosition / drivers.size
      drivers.forEach {
        val driver = mapOfDrivers[it.id()]!!
        val newDriverElo = driver.currentElo().value + eloDeltaPerDriver
        mapOfDrivers[driver.id()] = driver.updateElo(newDriverElo, raceDate.date)
      }
    }

    return mapOfDrivers.values.toList()
  }

  private fun List<Driver>.getAverageElo(): Int = this.map { it.currentElo().value }.average().toInt()

  fun calculateEloDelta(elo: Int, rivalElo: Int, raceResult: RaceResult, kReducer: Int): Int {
    val qA = calculateQ(rating = elo)
    val qB = calculateQ(rating = rivalElo)

    val e = calculateE(qA = qA, qB = qB)
    val s = calculateS(raceResult)

    val k = if (kReducer == 1) K else (K / (kReducer - 1)) * 1.5f

    return round(elo + k * (s - e)).toInt() - elo
  }

  private fun calculateQ(rating: Int): Double = 10.0.pow(rating / 400.0)

  private fun calculateE(qA: Double, qB: Double): Double = qA / (qA + qB)

  private fun calculateS(raceResult: RaceResult) =
    when (raceResult) {
      WIN -> 1.0
      LOSE -> 0.0
      DRAW -> 0.5
    }

  private companion object {
    const val K = 32.0
  }
}

enum class RaceResult {
  WIN,
  LOSE,
  DRAW
}
