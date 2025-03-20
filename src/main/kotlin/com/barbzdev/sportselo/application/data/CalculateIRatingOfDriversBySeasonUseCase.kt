package com.barbzdev.sportselo.application.data

import com.barbzdev.sportselo.domain.ConstructorPerformance
import com.barbzdev.sportselo.domain.Driver
import com.barbzdev.sportselo.domain.Race
import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.DriverRepository
import com.barbzdev.sportselo.domain.repository.SeasonRepository
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.sportselo.domain.service.IRatingCalculator

class CalculateIRatingOfDriversBySeasonUseCase(
  private val seasonRepository: SeasonRepository,
  private val driverRepository: DriverRepository,
  private val iRatingCalculator: IRatingCalculator,
  private val theoreticalPerformanceRepository: TheoreticalPerformanceRepository,
  private val instrumentation: UseCaseInstrumentation
) {
  operator fun invoke(request: CalculateIRatingOfDriversBySeasonRequest) = instrumentation {
    request
      .findSeason()
      ?.races()
      ?.orderByRoundAsc()
      ?.forEach { race -> race.calculateIRatingOfDrivers(request.season).updateDriversIRating() }
      ?.let { CalculateIRatingOfDriversOfBySeasonSuccess } ?: CalculateIRatingOfDriversOfANonExistentSeason
  }

  private fun CalculateIRatingOfDriversBySeasonRequest.findSeason() = seasonRepository.findBy(SeasonYear(this.season))

  private fun List<Race>.orderByRoundAsc() = this.sortedBy { it.round().value }

  private fun Race.calculateIRatingOfDrivers(seasonYear: Int): List<Driver> {
    val updatedDrivers = mutableListOf<Driver>()
    val sof = iRatingCalculator.calculateSOF(this.results().map { it.driver.currentIRating() })
    val theoreticalPerformances = theoreticalPerformanceRepository.findBy(SeasonYear(seasonYear))
    val raceDate = this.occurredOn()
    results().forEach { result ->
      val constructorPerformance =
        theoreticalPerformances?.getConstructorPerformance(result.constructor.id())
          ?: ConstructorPerformance(result.constructor, 0f)
      val iRatingDelta =
        iRatingCalculator.calculateIRatingDelta(
          result.driver.currentIRating(), constructorPerformance, sof, result.position, results().size)

      val updatedDriver =
        result.driver.updateIRating(result.driver.currentIRating().value + iRatingDelta, raceDate.date)
      updatedDrivers.add(updatedDriver)
    }
    return updatedDrivers
  }

  private fun List<Driver>.updateDriversIRating() = this.forEach { driverRepository.save(it) }
}

data class CalculateIRatingOfDriversBySeasonRequest(val season: Int)

sealed class CalculateIRatingOfDriversBySeasonResponse

data object CalculateIRatingOfDriversOfBySeasonSuccess : CalculateIRatingOfDriversBySeasonResponse()

data object CalculateIRatingOfDriversOfANonExistentSeason : CalculateIRatingOfDriversBySeasonResponse()
