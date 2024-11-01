package com.barbzdev.f1elo.application

import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.RaceDate
import com.barbzdev.f1elo.domain.RaceResult
import com.barbzdev.f1elo.domain.SeasonYear
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.domain.service.EloCalculator

class CalculateEloOfDriversBySeasonUseCase(
  private val seasonRepository: SeasonRepository,
  private val driverRepository: DriverRepository,
  private val eloCalculator: EloCalculator
) {
  operator fun invoke(request: CalculateEloOfDriversBySeasonRequest) =
    request
      .findSeason()
      ?.races()
      ?.orderByRoundAsc()
      ?.forEach { race ->
        race.groupDriversByTeam().orderDriversAscByPosition().calculateEloOfDrivers(race.occurredOn()).saveDriversElo()
      }
      ?.let { CalculateEloOfDriversOfBySeasonSuccess } ?: CalculateEloOfDriversOfANonExistentSeason

  private fun CalculateEloOfDriversBySeasonRequest.findSeason() = seasonRepository.findBy(SeasonYear(this.season))

  private fun List<Race>.orderByRoundAsc() = this.sortedBy { it.round().value }

  private fun Race.groupDriversByTeam() = this.results().groupBy { it.constructor }

  private fun Map<Constructor, List<RaceResult>>.orderDriversAscByPosition() =
    this.mapValues { (_, results) -> results.sortedBy { it.position } }

  private fun Map<Constructor, List<RaceResult>>.calculateEloOfDrivers(occurredOn: RaceDate): List<Driver> {
    val updatedDrivers = mutableListOf<Driver>()
    this.forEach { (_, results) ->
      val mapDriversByPosition = results.groupBy { it.position }.mapValues { it.value.map { result -> result.driver } }

      val eloOfDriversUpdated = eloCalculator.calculateEloRatingsByPosition(mapDriversByPosition, occurredOn)
      updatedDrivers.addAll(eloOfDriversUpdated)
    }
    return updatedDrivers
  }

  private fun List<Driver>.saveDriversElo() = this.forEach { driverRepository.save(it) }
}

data class CalculateEloOfDriversBySeasonRequest(val season: Int)

sealed class CalculateEloOfDriversBySeasonResponse

data object CalculateEloOfDriversOfBySeasonSuccess : CalculateEloOfDriversBySeasonResponse()

data object CalculateEloOfDriversOfANonExistentSeason : CalculateEloOfDriversBySeasonResponse()
