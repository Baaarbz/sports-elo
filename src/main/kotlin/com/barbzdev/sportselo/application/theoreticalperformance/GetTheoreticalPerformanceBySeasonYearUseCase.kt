package com.barbzdev.sportselo.application.theoreticalperformance

import com.barbzdev.sportselo.domain.TheoreticalPerformance
import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository

class GetTheoreticalPerformanceBySeasonYearUseCase(
  private val instrumentation: UseCaseInstrumentation,
  private val repository: TheoreticalPerformanceRepository
) {
  operator fun invoke(
    request: GetTheoreticalPerformanceBySeasonYearRequest
  ): GetTheoreticalPerformanceBySeasonYearResponse = instrumentation {
    request.findTheoreticalPerformanceBySeasonYear()?.mapToResponse() ?: GetTheoreticalPerformanceBySeasonYearNotFound
  }

  private fun GetTheoreticalPerformanceBySeasonYearRequest.findTheoreticalPerformanceBySeasonYear():
    TheoreticalPerformance? = repository.findBy(SeasonYear(season))

  private fun TheoreticalPerformance.mapToResponse(): GetTheoreticalPerformanceBySeasonYearResponse =
    GetTheoreticalPerformanceBySeasonYearSuccess(
      seasonYear = seasonYear().value,
      isAnalyzedData = isAnalyzedSeason(),
      theoreticalConstructorPerformances =
        constructorsPerformance().map {
          GetTheoreticalPerformanceBySeasonYearConstructorPerformance(
            constructorId = it.constructor.id().value, performance = it.performance)
        },
      dataOrigin =
        dataOrigin()?.let { GetTheoreticalPerformanceBySeasonYearDataOrigin(source = it.source, url = it.url) })
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

data class GetTheoreticalPerformanceBySeasonYearConstructorPerformance(
  val constructorId: String,
  val performance: Float
)
