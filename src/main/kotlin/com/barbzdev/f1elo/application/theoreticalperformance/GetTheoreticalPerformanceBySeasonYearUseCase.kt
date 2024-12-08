package com.barbzdev.f1elo.application.theoreticalperformance

import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation

class GetTheoreticalPerformanceBySeasonYearUseCase(private val instrumentation: UseCaseInstrumentation) {
  operator fun invoke(
    request: GetTheoreticalPerformanceBySeasonYearRequest
  ): GetTheoreticalPerformanceBySeasonYearResponse {
    TODO("Not yet implemented")
  }
}

data class GetTheoreticalPerformanceBySeasonYearRequest(val season: Int)

sealed class GetTheoreticalPerformanceBySeasonYearResponse
