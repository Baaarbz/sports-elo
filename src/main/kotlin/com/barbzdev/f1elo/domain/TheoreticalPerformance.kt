package com.barbzdev.f1elo.domain

import com.barbzdev.f1elo.domain.common.SeasonYear

class TheoreticalPerformance private constructor(
  private val seasonYear: SeasonYear,
  private val isAnalyzedData: IsAnalyzedData,
  private val constructorsPerformance: List<ConstructorPerformance>
) {
  fun seasonYear() = seasonYear

  fun isAnalyzedData() = isAnalyzedData.value

  fun constructorsPerformance() = constructorsPerformance

  fun getConstructorPerformance(constructorId: ConstructorId): Float? =
    constructorsPerformance.find { it.constructor.id() == constructorId }?.performance

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TheoreticalPerformance

    if (seasonYear != other.seasonYear) return false
    if (isAnalyzedData != other.isAnalyzedData) return false
    if (constructorsPerformance != other.constructorsPerformance) return false

    return true
  }

  override fun hashCode(): Int {
    var result = seasonYear.hashCode()
    result = 31 * result + isAnalyzedData.hashCode()
    result = 31 * result + constructorsPerformance.hashCode()
    return result
  }

  override fun toString(): String {
    return "TheoreticalPerformance(seasonYear=$seasonYear, isAnalyzedData=$isAnalyzedData, constructorsPerformance=$constructorsPerformance)"
  }

  companion object {
    fun create(seasonYear: Int, isAnalyzedData: Boolean, constructorsPerformance: List<ConstructorPerformance>): TheoreticalPerformance {
      return TheoreticalPerformance(SeasonYear(seasonYear), IsAnalyzedData(isAnalyzedData), constructorsPerformance)
    }
  }
}

data class IsAnalyzedData(val value: Boolean)
data class ConstructorPerformance(val constructor: Constructor, val performance: Float)
