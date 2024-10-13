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
    val seasonToLoad = getSeasonToLoad()

    if (seasonToLoad.isCurrentSeason()) {
      return GatherRacesBySeasonUpToDate
    }

    seasonToLoad
      .loadSeason()
      .saveRaces()

    publishSeasonLoadedDomainEvent(seasonToLoad)
    return GatherRacesBySeasonSuccess
  }

  private fun getSeasonToLoad() = (f1Repository.getLastSeasonLoaded()?.value?.plus(1))
    ?: FIRST_FORMULA_1_SEASON

  private fun Int.isCurrentSeason() = this >= now().year

  private fun Int.loadSeason() = f1Repository.gatherRacesBySeason(Season(this))

  private fun List<Race>.saveRaces() = raceRepository.saveAll(this)

  private fun publishSeasonLoadedDomainEvent(season: Int) =
    seasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(Season(season)))

  private companion object {
    const val FIRST_FORMULA_1_SEASON = 1950
  }
}

sealed class GatherRacesBySeasonResponse
data object GatherRacesBySeasonUpToDate : GatherRacesBySeasonResponse()
data object GatherRacesBySeasonSuccess : GatherRacesBySeasonResponse()
