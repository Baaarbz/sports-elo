package com.barbzdev.sportselo.domain

import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.exception.NonValidPerformanceException

class TheoreticalPerformance
private constructor(
  private val seasonYear: SeasonYear,
  private val isAnalyzedSeason: IsAnalyzedSeason,
  private val constructorsPerformance: List<ConstructorPerformance>,
  private val dataOrigin: DataOrigin?
) {
  fun seasonYear() = seasonYear

  fun isAnalyzedSeason() = isAnalyzedSeason.value

  fun constructorsPerformance() = constructorsPerformance.sortedBy { it.performance }

  fun dataOrigin() = dataOrigin

  fun getConstructorPerformance(constructorId: ConstructorId): ConstructorPerformance? =
    constructorsPerformance.find { it.constructor.id() == constructorId }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TheoreticalPerformance

    if (seasonYear != other.seasonYear) return false
    if (isAnalyzedSeason != other.isAnalyzedSeason) return false
    if (constructorsPerformance != other.constructorsPerformance) return false
    if (dataOrigin != other.dataOrigin) return false

    return true
  }

  override fun hashCode(): Int {
    var result = seasonYear.hashCode()
    result = 31 * result + isAnalyzedSeason.hashCode()
    result = 31 * result + constructorsPerformance.hashCode()
    result = 31 * result + dataOrigin.hashCode()
    return result
  }

  override fun toString(): String =
    "TheoreticalPerformance(seasonYear=$seasonYear, isAnalyzedSeason=$isAnalyzedSeason, constructorsPerformance=$constructorsPerformance, dataOrigin=$dataOrigin)"

  companion object {
    fun create(
      seasonYear: Int,
      isAnalyzedSeason: Boolean,
      constructorsPerformance: List<ConstructorPerformance>,
      dataOriginSource: String?,
      dataOriginUrl: String?
    ): TheoreticalPerformance {
      val dataOrigin =
        if (dataOriginUrl == null || dataOriginSource == null) null
        else DataOrigin(source = dataOriginSource, url = dataOriginUrl)
      return TheoreticalPerformance(
        seasonYear = SeasonYear(seasonYear),
        isAnalyzedSeason = IsAnalyzedSeason(isAnalyzedSeason),
        constructorsPerformance = constructorsPerformance,
        dataOrigin = dataOrigin,
      )
    }
  }
}

data class IsAnalyzedSeason(val value: Boolean)

data class ConstructorPerformance(val constructor: Constructor, val performance: Float) {
  init {
    require(performance >= 0) { throw NonValidPerformanceException(performance) }
  }
}

data class DataOrigin(val source: String, val url: String)
