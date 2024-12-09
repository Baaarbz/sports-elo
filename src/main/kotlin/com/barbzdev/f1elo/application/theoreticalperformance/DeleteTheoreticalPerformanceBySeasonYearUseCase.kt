package com.barbzdev.f1elo.application.theoreticalperformance

import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository

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
