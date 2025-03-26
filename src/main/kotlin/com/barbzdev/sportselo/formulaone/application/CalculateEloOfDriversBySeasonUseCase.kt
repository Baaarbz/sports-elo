package com.barbzdev.sportselo.formulaone.application

import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.core.domain.service.EloCalculator
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear

class CalculateEloOfDriversBySeasonUseCase(
  private val seasonRepository: SeasonRepository,
  private val driverRepository: DriverRepository,
  private val eloCalculator: EloCalculator,
  private val instrumentation: UseCaseInstrumentation
) {
  operator fun invoke(request: CalculateEloOfDriversBySeasonRequest): CalculateEloOfDriversBySeasonResponse =
    instrumentation {
      val season = request.findSeason() ?: return@instrumentation CalculateEloOfDriversBySeasonResponse.SeasonNotFound

      season.getRacesOrderedByRound().forEach { race -> calculateEloOfDrivers(race) }

      CalculateEloOfDriversBySeasonResponse.Success
    }

  private fun CalculateEloOfDriversBySeasonRequest.findSeason() = seasonRepository.findBy(SeasonYear(this.season))

  private fun Season.getRacesOrderedByRound() = this.races().sortedBy { it.round().value }

  private fun calculateEloOfDrivers(race: Race) {
    val updatedDrivers = mutableListOf<Driver>()
    for (result in race.results()) {
      val driverToUpdate = result.driver
      val rivalsElo = race.results().filter { it.driver != driverToUpdate }.map { it.driver.currentElo() }

      val eloDelta = eloCalculator.calculate(driverToUpdate.currentElo(), rivalsElo, result.position)
      val updatedElo = driverToUpdate.currentElo().value + eloDelta
      updatedDrivers.add(driverToUpdate.updateElo(updatedElo, race.occurredOn().date.value))
    }

    driverRepository.saveAll(updatedDrivers)
  }
}

data class CalculateEloOfDriversBySeasonRequest(val season: Int)

sealed class CalculateEloOfDriversBySeasonResponse {
  data object SeasonNotFound : CalculateEloOfDriversBySeasonResponse()

  data object Success : CalculateEloOfDriversBySeasonResponse()
}
