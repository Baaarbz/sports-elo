package com.barbzdev.f1elo.domain

import com.barbzdev.f1elo.domain.common.SeasonId
import com.barbzdev.f1elo.domain.exception.NonValidPerformanceException

class TheoreticalPerformance
private constructor(
  private val seasonId: SeasonId,
  private val isAnalyzedSeason: IsAnalyzedSeason,
  private val constructorsPerformance: List<ConstructorPerformance>
) {
  fun seasonId() = seasonId

  fun isAnalyzedSeason() = isAnalyzedSeason.value

  fun constructorsPerformance() = constructorsPerformance

  fun getConstructorPerformance(constructorId: ConstructorId): Float? =
    constructorsPerformance.find { it.constructor.id() == constructorId }?.performance

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TheoreticalPerformance

    if (seasonId != other.seasonId) return false
    if (isAnalyzedSeason != other.isAnalyzedSeason) return false
    if (constructorsPerformance != other.constructorsPerformance) return false

    return true
  }

  override fun hashCode(): Int {
    var result = seasonId.hashCode()
    result = 31 * result + isAnalyzedSeason.hashCode()
    result = 31 * result + constructorsPerformance.hashCode()
    return result
  }

  override fun toString(): String {
    return "TheoreticalPerformance(seasonId=$seasonId, isAnalyzedSeason=$isAnalyzedSeason, constructorsPerformance=$constructorsPerformance)"
  }

  companion object {
    fun create(
      seasonId: String,
      isAnalyzedSeason: Boolean,
      constructorsPerformance: List<ConstructorPerformance>
    ): TheoreticalPerformance {
      return TheoreticalPerformance(SeasonId(seasonId), IsAnalyzedSeason(isAnalyzedSeason), constructorsPerformance)
    }
  }
}

data class IsAnalyzedSeason(val value: Boolean)

data class ConstructorPerformance(val constructor: Constructor, val performance: Float) {
  init {
    require(performance >= 0) { throw NonValidPerformanceException(performance) }
  }
}
