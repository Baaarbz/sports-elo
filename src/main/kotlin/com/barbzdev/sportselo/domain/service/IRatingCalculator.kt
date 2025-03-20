package com.barbzdev.sportselo.domain.service

import com.barbzdev.sportselo.domain.ConstructorPerformance
import com.barbzdev.sportselo.domain.common.IRating
import kotlin.math.pow
import kotlin.math.round

class IRatingCalculator {

  fun calculateSOF(drivers: List<IRating>) = drivers.map { it.value }.average()

  fun calculateIRatingDelta(
    driverRating: IRating,
    constructorPerformance: ConstructorPerformance,
    sof: Double,
    driverResult: Int,
    driversInGrid: Int
  ): Int {
    val k = determineKFactor(driversInGrid)
    val adjustedPerformance = calculateAdjustedPerformance(driverRating, constructorPerformance)
    val expectedPerformance = calculateExpectedPerformance(sof, adjustedPerformance)
    val actualPerformance = calculateActualPerformance(driverResult, driversInGrid)
    return round(k * (actualPerformance - expectedPerformance)).toInt()
  }

  private fun calculateAdjustedPerformance(driverRating: IRating, constructorPerformance: ConstructorPerformance) =
    driverRating.value - (TIME_DEFICIT_SCALING_FACTOR * constructorPerformance.performance)

  private fun determineKFactor(driversInGrid: Int) = 30.0 + (70.0 / driversInGrid)

  private fun calculateExpectedPerformance(sof: Double, adjustedPerformance: Double) =
    1.0 / (1.0 + 10.0.pow((sof - adjustedPerformance) / 400.0))

  private fun calculateActualPerformance(driverResult: Int, driversInGrid: Int) =
    1.0 - ((driverResult - 1.0) / (driversInGrid - 1.0))

  private companion object {
    const val TIME_DEFICIT_SCALING_FACTOR = 50.0
  }
}
