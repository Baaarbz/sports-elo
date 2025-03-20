package com.barbzdev.sportselo.formulaone.application.data

import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceDate
import com.barbzdev.sportselo.domain.RaceResult
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.core.domain.service.EloCalculator
import com.barbzdev.sportselo.core.domain.service.Result

class CalculateEloOfDriversBySeasonUseCase(
  private val seasonRepository: SeasonRepository,
  private val driverRepository: DriverRepository,
  private val eloCalculator: EloCalculator,
  private val instrumentation: UseCaseInstrumentation
) {
  operator fun invoke(request: CalculateEloOfDriversBySeasonRequest) = instrumentation {
    request
      .findSeason()
      ?.races()
      ?.orderByRoundAsc()
      ?.forEach { race -> race.groupDriversByTeam().calculateEloOfDrivers(race.occurredOn()).updateDriversElo() }
      ?.let { CalculateEloOfDriversOfBySeasonSuccess } ?: CalculateEloOfDriversOfANonExistentSeason
  }

  private fun CalculateEloOfDriversBySeasonRequest.findSeason() = seasonRepository.findBy(SeasonYear(this.season))

  private fun List<Race>.orderByRoundAsc() = this.sortedBy { it.round().value }

  private fun Race.groupDriversByTeam() = this.results().groupBy { it.constructor }

  private fun List<Driver>.updateDriversElo() = this.forEach { driverRepository.save(it) }
}

data class CalculateEloOfDriversBySeasonRequest(val season: Int)

sealed class CalculateEloOfDriversBySeasonResponse

data object CalculateEloOfDriversOfBySeasonSuccess : CalculateEloOfDriversBySeasonResponse()

data object CalculateEloOfDriversOfANonExistentSeason : CalculateEloOfDriversBySeasonResponse()
