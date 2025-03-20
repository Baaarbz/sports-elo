package com.barbzdev.sportselo.domain.repository

import com.barbzdev.sportselo.domain.TheoreticalPerformance
import com.barbzdev.sportselo.domain.common.SeasonYear

interface TheoreticalPerformanceRepository {
  fun deleteBy(season: SeasonYear)

  fun findBy(season: SeasonYear): TheoreticalPerformance?

  fun save(theoreticalPerformance: TheoreticalPerformance)
}
