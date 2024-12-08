package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.ConstructorPerformance
import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.factory.ConstructorFactory.aConstructor

object TheoreticalPerformanceFactory {

  fun aTheoreticalPerformance() = TheoreticalPerformance.create(
    seasonYear = 2021,
    isAnalyzedSeason = true,
    constructorsPerformance = listOf(
      ConstructorPerformance(aConstructor(), 0f),
      ConstructorPerformance(aConstructor(), 0.234f)
    )
  )
}
