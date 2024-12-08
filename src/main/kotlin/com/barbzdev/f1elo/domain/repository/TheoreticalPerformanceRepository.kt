package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.domain.common.SeasonYear

interface TheoreticalPerformanceRepository {
  fun deleteBy(season: SeasonYear)

  fun findBy(season: SeasonYear): TheoreticalPerformance?

  fun save(theoreticalPerformance: TheoreticalPerformance)
}
