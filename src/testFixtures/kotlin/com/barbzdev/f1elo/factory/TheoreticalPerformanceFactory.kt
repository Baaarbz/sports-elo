package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.ConstructorPerformance
import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.factory.ConstructorFactory.aConstructor

object TheoreticalPerformanceFactory {

  fun aTheoreticalPerformance() =
    TheoreticalPerformance.create(
      seasonYear = (1960..2024).random(),
      isAnalyzedSeason = true,
      constructorsPerformance =
        listOf(ConstructorPerformance(aConstructor(), 0f), ConstructorPerformance(aConstructor(), "%.3f".format((0..2).random() + Math.random()).toFloat())))

  fun aTheoreticalPerformance(seasonYear: Int) =
    TheoreticalPerformance.create(
      seasonYear = seasonYear,
      isAnalyzedSeason = true,
      constructorsPerformance =
        listOf(ConstructorPerformance(aConstructor(), 0f), ConstructorPerformance(aConstructor(), "%.3f".format((0..2).random() + Math.random()).toFloat())))
}
