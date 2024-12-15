package com.barbzdev.f1elo.application.data

import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.exception.EloReprocessingFailedException
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository

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
