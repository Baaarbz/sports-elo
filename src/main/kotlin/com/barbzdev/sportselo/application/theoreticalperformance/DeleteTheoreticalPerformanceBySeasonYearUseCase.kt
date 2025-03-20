package com.barbzdev.sportselo.application.theoreticalperformance

import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository

class DeleteTheoreticalPerformanceBySeasonYearUseCase(
  private val instrumentation: UseCaseInstrumentation,
  private val repository: TheoreticalPerformanceRepository
) {
  operator fun invoke(
    request: DeleteTheoreticalPerformanceBySeasonYearRequest
  ): DeleteTheoreticalPerformanceBySeasonYearResponse = instrumentation {
    request.deleteTheoreticalPerformanceBySeasonYear()

    DeleteTheoreticalPerformanceBySeasonYearSuccess
  }

  private fun DeleteTheoreticalPerformanceBySeasonYearRequest.deleteTheoreticalPerformanceBySeasonYear() =
    repository.deleteBy(SeasonYear(season))
}

data class DeleteTheoreticalPerformanceBySeasonYearRequest(val season: Int)

sealed class DeleteTheoreticalPerformanceBySeasonYearResponse

data object DeleteTheoreticalPerformanceBySeasonYearSuccess : DeleteTheoreticalPerformanceBySeasonYearResponse()
