package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.TheoreticalPerformance

interface TheoreticalPerformanceRepository {
  fun deleteBy(season: SeasonYear)

  fun findBy(season: SeasonYear): TheoreticalPerformance?

  fun save(theoreticalPerformance: TheoreticalPerformance)
}
