package com.barbzdev.sportselo.application.theoreticalperformance

import com.barbzdev.sportselo.domain.ConstructorId
import com.barbzdev.sportselo.domain.ConstructorPerformance
import com.barbzdev.sportselo.domain.TheoreticalPerformance
import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.exception.AddTheoreticalPerformanceConstructorNotFoundException
import com.barbzdev.sportselo.domain.exception.NonValidPerformanceException
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.ConstructorRepository
import com.barbzdev.sportselo.domain.repository.SeasonRepository
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository

class AddTheoreticalPerformanceUseCase(
  private val instrumentation: UseCaseInstrumentation,
  private val theoreticalPerformanceRepository: TheoreticalPerformanceRepository,
  private val seasonRepository: SeasonRepository,
  private val constructorRepository: ConstructorRepository
) {

  operator fun invoke(request: AddTheoreticalPerformanceRequest): AddTheoreticalPerformanceResponse = instrumentation {
    if (request.isSeasonNotAvailable()) return@instrumentation AddTheoreticalPerformanceOverANonExistentSeason

    if (theoreticalPerformanceRepository.findBy(SeasonYear(request.seasonYear)) != null) {
      return@instrumentation AddTheoreticalPerformanceAlreadyCreated
    }

    runCatching { request.mapToDomain().save() }
      .onFailure {
        return@instrumentation when (it) {
          is AddTheoreticalPerformanceConstructorNotFoundException -> AddTheoreticalPerformanceOverAnInvalidConstructor
          is NonValidPerformanceException -> AddTheoreticalPerformanceOverAnInvalidPerformance
          else -> throw it
        }
      }

    AddTheoreticalPerformanceSuccess
  }

  private fun AddTheoreticalPerformanceRequest.isSeasonNotAvailable() =
    seasonRepository.getSeasonIdBy(SeasonYear(seasonYear)) == null

  private fun AddTheoreticalPerformanceRequest.mapToDomain(): TheoreticalPerformance =
    TheoreticalPerformance.create(
      seasonYear = seasonYear,
      isAnalyzedSeason = isAnalyzedData,
      constructorsPerformance =
        theoreticalConstructorPerformances.map {
          ConstructorPerformance(
            constructor =
              constructorRepository.findBy(ConstructorId(it.constructorId))
                ?: throw AddTheoreticalPerformanceConstructorNotFoundException(it.constructorId),
            performance = it.performance)
        },
      dataOriginSource = dataOrigin.source,
      dataOriginUrl = dataOrigin.url)

  private fun TheoreticalPerformance.save() = theoreticalPerformanceRepository.save(this)
}

data class AddTheoreticalPerformanceRequest(
  val seasonYear: Int,
  val isAnalyzedData: Boolean,
  val dataOrigin: AddTheoreticalPerformanceDataOrigin,
  val theoreticalConstructorPerformances: List<AddTheoreticalPerformanceConstructorPerformance>
)

data class AddTheoreticalPerformanceConstructorPerformance(val constructorId: String, val performance: Float)

data class AddTheoreticalPerformanceDataOrigin(val source: String, val url: String)

sealed class AddTheoreticalPerformanceResponse

data object AddTheoreticalPerformanceSuccess : AddTheoreticalPerformanceResponse()

data object AddTheoreticalPerformanceOverANonExistentSeason : AddTheoreticalPerformanceResponse()

data object AddTheoreticalPerformanceOverAnInvalidConstructor : AddTheoreticalPerformanceResponse()

data object AddTheoreticalPerformanceOverAnInvalidPerformance : AddTheoreticalPerformanceResponse()

data object AddTheoreticalPerformanceAlreadyCreated : AddTheoreticalPerformanceResponse()
