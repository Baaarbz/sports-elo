package com.barbzdev.sportselo.formulaone.application.data

import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository

class ReprocessIRatingUseCase(
  private val calculateIRatingOfDriversBySeasonUseCase: CalculateIRatingOfDriversBySeasonUseCase,
  private val driverRepository: DriverRepository,
  private val seasonRepository: SeasonRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(): ReprocessIRatingResponse = instrumentation {
    resetIRatingOfAllDrivers()

    getAllExistingSeasonsYears().reprocessIRatingOfDriversBySeason()

    ReprocessIRatingSuccess
  }

  private fun resetIRatingOfAllDrivers() {
    driverRepository.findAll().forEach {
      val resetDriver = it.resetIRating()
      driverRepository.save(resetDriver)
    }
  }

  private fun getAllExistingSeasonsYears() = seasonRepository.findAllSeasonsYears().sortedBy { it.value }

  private fun List<SeasonYear>.reprocessIRatingOfDriversBySeason() {
    forEach {
      val response = calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(it.value))
      if (response !is CalculateIRatingOfDriversOfBySeasonSuccess) throw IRatingReprocessingFailedException(it.value)
    }
  }
}

sealed class ReprocessIRatingResponse

data object ReprocessIRatingSuccess : ReprocessIRatingResponse()
