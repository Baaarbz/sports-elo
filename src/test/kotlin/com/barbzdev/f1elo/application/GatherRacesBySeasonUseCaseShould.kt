package com.barbzdev.f1elo.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.factory.RaceFactory
import com.barbzdev.f1elo.factory.SeasonFactory.aF1Season
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.factory.SeasonFactory.f1Seasons
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import java.time.LocalDate.now
import org.junit.jupiter.api.Test

class GatherRacesBySeasonUseCaseShould {

  private val f1Repository: F1Repository = mockk()
  private val seasonRepository: SeasonRepository = mockk()
  private val driverRepository: DriverRepository = mockk()
  private val seasonDomainEventPublisher: SeasonDomainEventPublisher = mockk(relaxed = true)

  private val gatherRacesBySeasonUseCase =
    GatherRacesBySeasonUseCase(f1Repository, seasonRepository, driverRepository, seasonDomainEventPublisher)

  @Test
  fun `return GatherRacesOverASeasonNonExistent when the season to load is the current season`() {
    every { seasonRepository.getLastSeasonLoaded() } returns aSeason(now().year)
    every { f1Repository.gatherAllSeasons() } returns listOf(aF1Season(now().year))

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesOverASeasonNonExistent::class)
    verify(exactly = 1) {
      seasonRepository.getLastSeasonLoaded()
      f1Repository.gatherAllSeasons()
    }
    verify(exactly = 0) {
      f1Repository.gatherRacesBySeason(any())
      driverRepository.findBy(any())
      seasonRepository.save(any())
      seasonDomainEventPublisher.publish(any())
    }
  }

  @Test
  fun `return GatherRacesBySeasonUpToDate when the season to load is the current season`() {
    every { seasonRepository.getLastSeasonLoaded() } returns aSeason(now().year)
    every { f1Repository.gatherAllSeasons() } returns listOf(aF1Season(now().year + 1))

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonUpToDate::class)
    verify(exactly = 1) {
      seasonRepository.getLastSeasonLoaded()
      f1Repository.gatherAllSeasons()
    }
    verify(exactly = 0) {
      f1Repository.gatherRacesBySeason(any())
      driverRepository.findBy(any())
      seasonRepository.save(any())
      seasonDomainEventPublisher.publish(any())
    }
  }

  @Test
  fun `return GatherRacesBySeasonSuccess when the season to load is not the current season`() {
    val aSeasonLoaded = aSeason()
    val aSeasonToLoad = aSeason(aSeasonLoaded.year().value + 1)
    every { seasonRepository.getLastSeasonLoaded() } returns aSeasonLoaded
    every { f1Repository.gatherAllSeasons() } returns f1Seasons
    every { f1Repository.gatherRacesBySeason(any()) } returns RaceFactory.aF1RacesFrom(aSeasonToLoad)
    every { driverRepository.findBy(any()) } returns null
    every { driverRepository.save(any()) } just Runs
    every { seasonRepository.save(any()) } just Runs

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonSuccess::class)
    verifyOrder {
      seasonRepository.getLastSeasonLoaded()
      f1Repository.gatherAllSeasons()
      f1Repository.gatherRacesBySeason(
        withArg { savedSeason -> assertThat(savedSeason.year()).isEqualTo(aSeasonToLoad.year()) })
      aSeasonToLoad.races().forEach { race ->
        race.results().forEach { result -> driverRepository.findBy(result.driver.id()) }
      }
      seasonRepository.save(withArg { savedSeason -> assertThat(savedSeason.year()).isEqualTo(aSeasonToLoad.year()) })
      seasonDomainEventPublisher.publish(
        withArg { seasonDomainEvent -> assertThat(seasonDomainEvent.season.year()).isEqualTo(aSeasonToLoad.year()) })
    }
  }

  @Test
  fun `return GatherRacesBySeasonSuccess when there are non previous season loaded`() {
    val aSeasonToLoad = aSeason(1950)
    every { seasonRepository.getLastSeasonLoaded() } returns null
    every { f1Repository.gatherAllSeasons() } returns f1Seasons
    every { f1Repository.gatherRacesBySeason(any()) } returns RaceFactory.aF1RacesFrom(aSeasonToLoad)
    every { driverRepository.findBy(any()) } returns null
    every { driverRepository.save(any()) } just Runs
    every { seasonRepository.save(any()) } just Runs

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonSuccess::class)
    verify {
      seasonRepository.save(withArg { savedSeason -> assertThat(savedSeason.year()).isEqualTo(aSeasonToLoad.year()) })
    }
  }
}
