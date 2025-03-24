package com.barbzdev.sportselo.core.domain.service

import com.barbzdev.sportselo.core.domain.service.Result.DRAW
import com.barbzdev.sportselo.core.domain.service.Result.LOSE
import com.barbzdev.sportselo.core.domain.service.Result.WIN
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import kotlin.math.pow
import kotlin.math.round

class EloCalculator {

  fun calculate(elo: Elo, rivalsElo: List<Elo>, sportsmanResult: Int): Int {
    val allEloCompetitors = rivalsElo.plus(elo)
    val sof = allEloCompetitors.map { it.value }.average()
    val k = 30.0 + (70.0 / allEloCompetitors.size)

    val expectedPerformance = 1.0 / (1.0 + 10.0.pow((sof - elo.value) / 400.0))

    val actualPerformance = 1.0 - ((sportsmanResult - 1.0) / (allEloCompetitors.size - 1.0))
    return round(k * (actualPerformance - expectedPerformance)).toInt()
  }

  fun calculate(teamElo: List<Elo>, rivalElo: List<Elo>, result: Result): Int {
    val averageTeamElo = teamElo.getAverageElo()
    val averageRivalElo = rivalElo.getAverageElo()

    return calculate(averageTeamElo, averageRivalElo, result)
  }

  fun calculate(elo: Elo, rivalElo: Elo, result: Result): Int {
    return calculate(elo.value, rivalElo.value, result)
  }

  private fun calculate(elo: Int, rivalElo: Int, result: Result): Int {
    val qA = calculateQ(rating = elo)
    val qB = calculateQ(rating = rivalElo)

    val e = calculateE(qA = qA, qB = qB)
    val s = calculateS(result)

    return round(elo + K * (s - e)).toInt() - elo
  }

  private fun calculateQ(rating: Int): Double = 10.0.pow(rating / 400.0)

  private fun calculateE(qA: Double, qB: Double): Double = qA / (qA + qB)

  private fun calculateS(result: Result) =
    when (result) {
      WIN -> 1.0
      LOSE -> 0.0
      DRAW -> 0.5
    }

  private fun List<Elo>.getAverageElo(): Int = this.map { it.value }.average().toInt()

  private companion object {
    const val K = 32.0
  }
}

enum class Result {
  WIN,
  LOSE,
  DRAW
}
