package com.barbzdev.f1elo.application.theoreticalperformance

import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository

class GetTheoreticalPerformanceBySeasonYearUseCase(
  private val instrumentation: UseCaseInstrumentation,
  private val repository: TheoreticalPerformanceRepository
) {
  operator fun invoke(request: GetTheoreticalPerformanceBySeasonYearRequest): GetTheoreticalPerformanceBySeasonYearResponse =
    instrumentation {
      request
        .findTheoreticalPerformanceBySeasonYear()
        ?.mapToResponse()
        ?: GetTheoreticalPerformanceBySeasonYearNotFound
    }

  private fun GetTheoreticalPerformanceBySeasonYearRequest.findTheoreticalPerformanceBySeasonYear(): TheoreticalPerformance? =
    repository.findBy(SeasonYear(season))

  private fun TheoreticalPerformance.mapToResponse(): GetTheoreticalPerformanceBySeasonYearResponse =
    GetTheoreticalPerformanceBySeasonYearSuccess(
      seasonYear = seasonYear().value,
      isAnalyzedData = isAnalyzedSeason(),
      theoreticalConstructorPerformances = constructorsPerformance().map {
        GetTheoreticalPerformanceBySeasonYearConstructorPerformance(
          constructorId = it.constructor.id().value,
          performance = it.performance
        )
      },
      dataOrigin = dataOrigin()?.let {
        GetTheoreticalPerformanceBySeasonYearDataOrigin(
          source = it.source,
          url = it.url
        )
      }
    )
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
