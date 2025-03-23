package com.barbzdev.sportselo.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.application.GatherRacesBySeasonResponse
import com.barbzdev.sportselo.formulaone.application.GatherRacesBySeasonUseCase
import com.barbzdev.sportselo.formulaone.domain.event.SeasonDomainEventPublisher
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.F1Repository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.formulaone.factory.RaceFactory
import com.barbzdev.sportselo.formulaone.factory.SeasonFactory.aF1Season
import com.barbzdev.sportselo.formulaone.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.formulaone.factory.SeasonFactory.f1Seasons
import com.barbzdev.sportselo.observability.instrumentationMock
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
  private val instrumentation: UseCaseInstrumentation = instrumentationMock<UseCaseInstrumentation>()

  private val gatherRacesBySeasonUseCase =
    GatherRacesBySeasonUseCase(
      f1Repository, seasonRepository, driverRepository, seasonDomainEventPublisher, instrumentation)

  @Test
  fun `return GatherRacesOverASeasonNonExistent when the season to load is the current season`() {
    every { seasonRepository.getLastYearLoaded() } returns SeasonYear(now().year)
    every { f1Repository.gatherAllSeasons() } returns listOf(aF1Season(now().year))

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonResponse.NonExistent::class)
    verify(exactly = 1) {
      seasonRepository.getLastYearLoaded()
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
    every { seasonRepository.getLastYearLoaded() } returns SeasonYear(now().year)
    every { f1Repository.gatherAllSeasons() } returns listOf(aF1Season(now().year + 1))

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonResponse.UpToDate::class)
    verify(exactly = 1) {
      seasonRepository.getLastYearLoaded()
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
    val aSeasonLoaded = SeasonYear(aSeason().year().value)
    val aSeasonToLoad = aSeason(aSeasonLoaded.value + 1)
    every { seasonRepository.getLastYearLoaded() } returns aSeasonLoaded
    every { f1Repository.gatherAllSeasons() } returns f1Seasons
    every { f1Repository.gatherRacesBySeason(any()) } returns RaceFactory.aF1RacesFrom(aSeasonToLoad)
    every { driverRepository.findBy(any()) } returns null
    every { driverRepository.save(any()) } just Runs
    every { seasonRepository.save(any()) } just Runs

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonResponse.Success::class)
    verifyOrder {
      seasonRepository.getLastYearLoaded()
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
    every { seasonRepository.getLastYearLoaded() } returns null
    every { f1Repository.gatherAllSeasons() } returns f1Seasons
    every { f1Repository.gatherRacesBySeason(any()) } returns RaceFactory.aF1RacesFrom(aSeasonToLoad)
    every { driverRepository.findBy(any()) } returns null
    every { driverRepository.save(any()) } just Runs
    every { seasonRepository.save(any()) } just Runs

    val result = gatherRacesBySeasonUseCase.invoke()

    assertThat(result).isInstanceOf(GatherRacesBySeasonResponse.Success::class)
    verify {
      seasonRepository.save(withArg { savedSeason -> assertThat(savedSeason.year()).isEqualTo(aSeasonToLoad.year()) })
    }
  }
}
