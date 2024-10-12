package com.barbzdev.f1elo.application

import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.common.Season
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.RaceRepository
import java.time.LocalDate.now

class GatherRacesBySeasonUseCase(
  private val f1Repository: F1Repository,
  private val raceRepository: RaceRepository,
  private val seasonDomainEventPublisher: SeasonDomainEventPublisher
) {

  operator fun invoke(): GatherRacesBySeasonResponse {
    val lastLoadedSeason = getLastSeasonLoaded()

    if (lastLoadedSeason.isLastCompletedSeason()) {
      return GatherRacesBySeasonUpToDate
    }

    lastLoadedSeason
      .loadNextSeasonRaces()
      .saveRaces()

    publishSeasonLoadedDomainEvent(lastLoadedSeason)
    return GatherRacesBySeasonSuccess
  }

  private fun getLastSeasonLoaded() = f1Repository.getLastSeasonLoaded()

  private fun Season.isLastCompletedSeason() = value == now().year - 1

  private fun Season.loadNextSeasonRaces() = f1Repository.gatherRacesBySeason(Season(value + 1))

  private fun List<Race>.saveRaces() = raceRepository.saveAll(this)

  private fun publishSeasonLoadedDomainEvent(lastLoadedSeason: Season) =
    seasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(Season(lastLoadedSeason.value + 1)))
}

sealed class GatherRacesBySeasonResponse
data object GatherRacesBySeasonUpToDate : GatherRacesBySeasonResponse()
data object GatherRacesBySeasonSuccess : GatherRacesBySeasonResponse()
