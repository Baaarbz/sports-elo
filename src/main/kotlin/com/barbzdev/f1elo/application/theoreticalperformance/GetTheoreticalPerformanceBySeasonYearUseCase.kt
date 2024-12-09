package com.barbzdev.f1elo.application.theoreticalperformance

import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository

class GetTheoreticalPerformanceBySeasonYearUseCase(
  private val instrumentation: UseCaseInstrumentation,
  private val repository: TheoreticalPerformanceRepository
) {
  operator fun invoke(request: GetTheoreticalPerformanceBySeasonYearRequest): GetTheoreticalPerformanceBySeasonYearResponse =
    instrumentation {
      TODO("Not yet implemented")
    }
}

data class GetTheoreticalPerformanceBySeasonYearRequest(val season: Int)

sealed class GetTheoreticalPerformanceBySeasonYearResponse
data object GetTheoreticalPerformanceBySeasonYearNotFound : GetTheoreticalPerformanceBySeasonYearResponse()
data class GetTheoreticalPerformanceBySeasonYearSuccess(
  val seasonYear: Int,
  val isAnalyzedData: Boolean,
  val theoreticalConstructorPerformances: List<GetTheoreticalPerformanceBySeasonYearConstructorPerformance>,
  val dataOrigin: GetTheoreticalPerformanceBySeasonYearDataOrigin?
) : GetTheoreticalPerformanceBySeasonYearResponse()

data class GetTheoreticalPerformanceBySeasonYearDataOrigin(val source: String, val url: String)
data class GetTheoreticalPerformanceBySeasonYearConstructorPerformance(val constructorId: String, val performance: Float)
