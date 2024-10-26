package com.barbzdev.f1elo.domain.service

import com.barbzdev.f1elo.domain.service.RaceResult.DRAW
import com.barbzdev.f1elo.domain.service.RaceResult.LOSE
import com.barbzdev.f1elo.domain.service.RaceResult.WIN
import kotlin.math.pow
import kotlin.math.round

object EloCalculator {
  fun calculateNewRating(elo: Int, rivalElo: Int, raceResult: RaceResult): Int {
    val qA = calculateQ(rating = elo)
    val qB = calculateQ(rating = rivalElo)

    val e = calculateE(qA = qA, qB = qB)
    val s = calculateS(raceResult)

    return round(elo + K * (s - e)).toInt()
  }

  private fun calculateQ(rating: Int): Double = 10.0.pow(rating / 400.0)

  private fun calculateE(qA: Double, qB: Double): Double = qA / (qA + qB)

  private fun calculateS(raceResult: RaceResult) =
    when (raceResult) {
      WIN -> 1.0
      LOSE -> 0.0
      DRAW -> 0.5
    }

  private const val K = 32.0
}

enum class RaceResult {
  WIN,
  LOSE,
  DRAW
}
