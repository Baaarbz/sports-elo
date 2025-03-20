package com.barbzdev.sportselo.formulaone.application.data

import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.core.domain.exception.EloReprocessingFailedException
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository

class ReprocessEloUseCase(
  private val calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase,
  private val driverRepository: DriverRepository,
  private val seasonRepository: SeasonRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(): ReprocessEloResponse = instrumentation {
    resetEloOfAllDrivers()

    getAllExistingSeasonsYears().reprocessEloOfDriversBySeason()

    ReprocessEloSuccess
  }

  private fun resetEloOfAllDrivers() {
    driverRepository.findAll().forEach {
      val resetDriver = it.resetElo()
      driverRepository.save(resetDriver)
    }
  }

  private fun getAllExistingSeasonsYears() = seasonRepository.findAllSeasonsYears().sortedBy { it.value }

  private fun List<SeasonYear>.reprocessEloOfDriversBySeason() {
    forEach {
      val response = calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(it.value))
      if (response !is CalculateEloOfDriversOfBySeasonSuccess) throw EloReprocessingFailedException(it.value)
    }
  }
}

sealed class ReprocessEloResponse

data object ReprocessEloSuccess : ReprocessEloResponse()
