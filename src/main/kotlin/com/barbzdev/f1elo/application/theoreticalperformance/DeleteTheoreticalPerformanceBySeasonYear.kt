package com.barbzdev.f1elo.application.theoreticalperformance

import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation

class DeleteTheoreticalPerformanceBySeasonYear(private val instrumentation: UseCaseInstrumentation) {

  operator fun invoke(
    request: DeleteTheoreticalPerformanceBySeasonYearRequest
  ): DeleteTheoreticalPerformanceBySeasonYearResponse {
    TODO("Not yet implemented")
  }
}

data class DeleteTheoreticalPerformanceBySeasonYearRequest(val season: Int)

sealed class DeleteTheoreticalPerformanceBySeasonYearResponse
