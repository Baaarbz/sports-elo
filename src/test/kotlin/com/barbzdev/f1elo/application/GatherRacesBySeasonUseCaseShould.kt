package com.barbzdev.f1elo.application

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.common.Season
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.RaceRepository
import com.barbzdev.f1elo.factory.RaceFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import java.time.LocalDate.now
import org.junit.jupiter.api.Test

class GatherRacesBySeasonUseCaseShould {

  private val f1Repository: F1Repository = mockk()
  private val raceRepository: RaceRepository = mockk(relaxed = true)
  private val seasonDomainEventPublisher: SeasonDomainEventPublisher = mockk(relaxed = true)

  private val gatherRacesBySeasonUseCase = GatherRacesBySeasonUseCase(
    f1Repository,
    raceRepository,
    seasonDomainEventPublisher
  )

  @Test
  fun `return GatherRacesBySeasonUpToDate when the season to load is the current season `() {
    every { f1Repository.getLastSeasonLoaded() } returns Season(now().year)

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonUpToDate::class)
    verify(exactly = 1) { f1Repository.getLastSeasonLoaded() }
    verify(exactly = 0) { f1Repository.gatherRacesBySeason(any()); raceRepository.saveAll(any()); seasonDomainEventPublisher.publish(any()) }
  }

  @Test
  fun `return GatherRacesBySeasonSuccess when the season to load is not the current season `() {
    val aRace = RaceFactory.aRace()
    every { f1Repository.getLastSeasonLoaded() } returns Season(1950)
    every { f1Repository.gatherRacesBySeason(any()) } returns listOf(aRace)

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonSuccess::class)
    verifyOrder {
      f1Repository.getLastSeasonLoaded()
      f1Repository.gatherRacesBySeason(Season(1951))
      raceRepository.saveAll(listOf(aRace))
      seasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(Season(1951)))
    }
  }
}
