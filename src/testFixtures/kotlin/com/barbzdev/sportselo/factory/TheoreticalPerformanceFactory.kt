package com.barbzdev.sportselo.factory

import com.barbzdev.sportselo.domain.Constructor
import com.barbzdev.sportselo.domain.ConstructorPerformance
import com.barbzdev.sportselo.domain.TheoreticalPerformance
import com.barbzdev.sportselo.factory.ConstructorFactory.aConstructor

object TheoreticalPerformanceFactory {

  fun aTheoreticalPerformance() =
    TheoreticalPerformance.create(
      seasonYear = (1960..2024).random(),
      isAnalyzedSeason = true,
      constructorsPerformance =
        listOf(ConstructorPerformance(aConstructor(), 0f), ConstructorPerformance(aConstructor(), 0.234f)),
      dataOriginUrl = "https://x.com/DeltaData_",
      dataOriginSource = "DeltaData",
    )

  fun aTheoreticalPerformance(seasonYear: Int, constructor: Constructor) =
    TheoreticalPerformance.create(
      seasonYear = seasonYear,
      isAnalyzedSeason = true,
      constructorsPerformance = listOf(ConstructorPerformance(constructor, 0f)),
      dataOriginUrl = "https://x.com/DeltaData_",
      dataOriginSource = "DeltaData",
    )
}
