package com.barbzdev.sportselo.formulaone.application

import com.barbzdev.sportselo.core.domain.exception.EloReprocessingFailedException
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear

class ReprocessEloUseCase(
  private val calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase,
  private val driverRepository: DriverRepository,
  private val seasonRepository: SeasonRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(): ReprocessEloResponse = instrumentation {
    resetEloOfAllDrivers()

    getAllExistingSeasonsYears().reprocessEloOfDriversBySeason()

    ReprocessEloResponse.Success
  }

  private fun resetEloOfAllDrivers() {
    val driversWithResetElo = mutableListOf<Driver>()
    driverRepository.findAll().forEach {
      driversWithResetElo.add(it.resetElo())
    }
  }

  private fun getAllExistingSeasonsYears() = seasonRepository.findAllSeasonsYears().sortedBy { it.value }

  private fun List<SeasonYear>.reprocessEloOfDriversBySeason() {
    forEach {
      val response = calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(it.value))
      if (response !is CalculateEloOfDriversBySeasonResponse.Success) throw EloReprocessingFailedException(it.value)
    }
  }
}

sealed class ReprocessEloResponse {
  data object Success : ReprocessEloResponse()
}
