package com.barbzdev.f1elo.domain.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class EloCalculatorShould {
  @Test
  fun `calculate new rating for a driver who wins against rival`() {
    val driverElo = 2000
    val rivalElo = 2000

    val result = EloCalculator.calculateNewRating(driverElo, rivalElo, RaceResult.WIN)

    val expectedNewElo = 2016
    assertThat(result).isEqualTo(expectedNewElo)
  }

  @Test
  fun `calculate new rating for a driver who loses against rival`() {
    val driverElo = 2000
    val rivalElo = 2000

    val result = EloCalculator.calculateNewRating(driverElo, rivalElo, RaceResult.LOSE)

    val expectedNewElo = 1984
    assertThat(result).isEqualTo(expectedNewElo)
  }

  @Test
  fun `calculate new rating for a driver who draws against rival`() {
    val driverElo = 2000
    val rivalElo = 2000

    val result = EloCalculator.calculateNewRating(driverElo, rivalElo, RaceResult.DRAW)

    val expectedNewElo = 2000
    assertThat(result).isEqualTo(expectedNewElo)
  }
}
