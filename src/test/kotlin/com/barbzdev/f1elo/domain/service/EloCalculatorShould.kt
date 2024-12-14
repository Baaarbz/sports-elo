package com.barbzdev.f1elo.domain.service

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.RaceDate
import com.barbzdev.f1elo.factory.DriverFactory.alonso
import com.barbzdev.f1elo.factory.DriverFactory.hamilton
import com.barbzdev.f1elo.factory.DriverFactory.raikkonen
import com.barbzdev.f1elo.factory.DriverFactory.verstappen
import org.junit.jupiter.api.Test

class EloCalculatorShould {

  private val eloCalculator = EloCalculator()

  @Test
  fun `calculate elo for team of 2 drivers with 2 cars`() {
    val givenMapOfPositionDrivers = mapOf(1 to listOf(alonso), 2 to listOf(hamilton))

    val result = eloCalculator.calculateEloRatingsByPosition(givenMapOfPositionDrivers, RaceDate(ANY_RACE_DATE))

    assertAll {
      assertThat(result.find { it.id() == alonso.id() }!!.currentElo().value).isEqualTo(2016)
      assertThat(result.find { it.id() == hamilton.id() }!!.currentElo().value).isEqualTo(1984)
    }
    verifyCurrentEloOccurredOn(result)
  }

  @Test
  fun `calculate elo for team of 3 drivers with 3 cars`() {
    val givenMapOfPositionDrivers = mapOf(1 to listOf(raikkonen), 2 to listOf(hamilton), 3 to listOf(alonso))

    val result = eloCalculator.calculateEloRatingsByPosition(givenMapOfPositionDrivers, RaceDate(ANY_RACE_DATE))

    assertAll {
      assertThat(result.find { it.id() == raikkonen.id() }!!.currentElo().value).isEqualTo(2024)
      assertThat(result.find { it.id() == hamilton.id() }!!.currentElo().value).isEqualTo(2000)
      assertThat(result.find { it.id() == alonso.id() }!!.currentElo().value).isEqualTo(1976)
    }
    verifyCurrentEloOccurredOn(result)
  }

  @Test
  fun `calculate elo for team of 4 drivers with 4 cars`() {
    val givenMapOfPositionDrivers =
      mapOf(1 to listOf(alonso), 2 to listOf(hamilton), 3 to listOf(verstappen), 4 to listOf(raikkonen))

    val result = eloCalculator.calculateEloRatingsByPosition(givenMapOfPositionDrivers, RaceDate(ANY_RACE_DATE))

    assertAll {
      assertThat(result.find { it.id() == alonso.id() }!!.currentElo().value).isEqualTo(2024)
      assertThat(result.find { it.id() == hamilton.id() }!!.currentElo().value).isEqualTo(2008)
      assertThat(result.find { it.id() == verstappen.id() }!!.currentElo().value).isEqualTo(1992)
      assertThat(result.find { it.id() == raikkonen.id() }!!.currentElo().value).isEqualTo(1976)
    }
    verifyCurrentEloOccurredOn(result)
  }

  @Test
  fun `calculate elo for team with one car with 2 drivers and other with 1 driver, non repeating drivers`() {
    val givenMapOfPositionDrivers = mapOf(1 to listOf(alonso, hamilton), 2 to listOf(raikkonen))

    val result = eloCalculator.calculateEloRatingsByPosition(givenMapOfPositionDrivers, RaceDate(ANY_RACE_DATE))

    assertAll {
      assertThat(result.find { it.id() == alonso.id() }!!.currentElo().value).isEqualTo(2008)
      assertThat(result.find { it.id() == hamilton.id() }!!.currentElo().value).isEqualTo(2008)
      assertThat(result.find { it.id() == raikkonen.id() }!!.currentElo().value).isEqualTo(1984)
    }
    verifyCurrentEloOccurredOn(result)
  }

  @Test
  fun `calculate elo for team with one car with 2 drivers and other with 1 driver, repeating drivers`() {
    val givenMapOfPositionDrivers = mapOf(1 to listOf(alonso, hamilton), 2 to listOf(alonso))

    val result = eloCalculator.calculateEloRatingsByPosition(givenMapOfPositionDrivers, RaceDate(ANY_RACE_DATE))

    assertAll {
      assertThat(result.find { it.id() == alonso.id() }!!.currentElo().value).isEqualTo(1992)
      assertThat(result.find { it.id() == hamilton.id() }!!.currentElo().value).isEqualTo(2008)
    }
    verifyCurrentEloOccurredOn(result)
  }

  private fun verifyCurrentEloOccurredOn(actualDrivers: List<Driver>) {
    actualDrivers.forEach { driver -> assertThat(driver.currentElo().occurredOn).isEqualTo(ANY_RACE_DATE) }
  }

  @Test
  fun `calculate delta rating for a driver who wins against rival`() {
    val driverElo = 2000
    val rivalElo = 2000

    val result = eloCalculator.calculateEloDelta(driverElo, rivalElo, RaceResult.WIN, 1)

    val expectedEloDelta = 16
    assertThat(result).isEqualTo(expectedEloDelta)
  }

  @Test
  fun `calculate delta rating for a driver who loses against rival`() {
    val driverElo = 2000
    val rivalElo = 2000

    val result = eloCalculator.calculateEloDelta(driverElo, rivalElo, RaceResult.LOSE, 1)

    val expectedEloDelta = -16
    assertThat(result).isEqualTo(expectedEloDelta)
  }

  @Test
  fun `calculate delta rating for a driver who draws against rival`() {
    val driverElo = 2000
    val rivalElo = 2000

    val result = eloCalculator.calculateEloDelta(driverElo, rivalElo, RaceResult.DRAW, 1)

    val expectedEloDelta = 0
    assertThat(result).isEqualTo(expectedEloDelta)
  }

  private companion object {
    const val ANY_RACE_DATE = "2024-01-01"
  }
}
