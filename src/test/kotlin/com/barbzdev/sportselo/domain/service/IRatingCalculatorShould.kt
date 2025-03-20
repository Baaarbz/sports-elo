package com.barbzdev.sportselo.domain.service

import com.barbzdev.sportselo.formulaone.domain.ConstructorPerformance
import com.barbzdev.sportselo.factory.ConstructorFactory.aConstructor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IRatingCalculatorShould {

  private val calculator = IRatingCalculator()

  @Test
  fun `calculate SOF correctly`() {
    val raceDriversIRating =
      listOf(IRating(1600, "2021-01-01"), IRating(1500, "2021-01-01"), IRating(1400, "2021-01-01"))

    val sof = calculator.calculateSOF(raceDriversIRating)
    assertThat(sof).isEqualTo(1500.0)
  }

  @Test
  fun `calculate IRating delta correctly`() {
    val constructorPerformance = ConstructorPerformance(aConstructor(), 0.8f)
    val sof = 1600.0
    val driverResult = 1
    val driversInGrid = 20
    val iRatingOfDriver = IRating(1500, "2021-01-01")

    val delta =
      calculator.calculateIRatingDelta(iRatingOfDriver, constructorPerformance, sof, driverResult, driversInGrid)

    assertThat(delta).isEqualTo(23)
  }
}
