package com.barbzdev.f1elo.domain

import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.exception.NonValidPerformanceException

class TheoreticalPerformance
private constructor(
  private val seasonYear: SeasonYear,
  private val isAnalyzedSeason: IsAnalyzedSeason,
  private val constructorsPerformance: List<ConstructorPerformance>
) {
  fun seasonYear() = seasonYear

  fun isAnalyzedSeason() = isAnalyzedSeason.value

  fun constructorsPerformance() = constructorsPerformance

  fun getConstructorPerformance(constructorId: ConstructorId): Float? =
    constructorsPerformance.find { it.constructor.id() == constructorId }?.performance

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TheoreticalPerformance

    if (seasonYear != other.seasonYear) return false
    if (isAnalyzedSeason != other.isAnalyzedSeason) return false
    if (constructorsPerformance != other.constructorsPerformance) return false

    return true
  }

  override fun hashCode(): Int {
    var result = seasonYear.hashCode()
    result = 31 * result + isAnalyzedSeason.hashCode()
    result = 31 * result + constructorsPerformance.hashCode()
    return result
  }

  override fun toString(): String {
    return "TheoreticalPerformance(seasonYear=$seasonYear, isAnalyzedSeason=$isAnalyzedSeason, constructorsPerformance=$constructorsPerformance)"
  }

  companion object {
    fun create(
      seasonYear: Int,
      isAnalyzedSeason: Boolean,
      constructorsPerformance: List<ConstructorPerformance>
    ): TheoreticalPerformance {
      return TheoreticalPerformance(SeasonYear(seasonYear), IsAnalyzedSeason(isAnalyzedSeason), constructorsPerformance)
    }
  }
}

data class IsAnalyzedSeason(val value: Boolean)

data class ConstructorPerformance(val constructor: Constructor, val performance: Float) {
  init {
    require(performance >= 0) { throw NonValidPerformanceException(performance) }
  }
}
