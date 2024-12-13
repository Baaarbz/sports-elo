package com.barbzdev.f1elo.application.data

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.exception.EloReprocessingFailedException
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.factory.DriverFactory.hamilton
import com.barbzdev.f1elo.factory.DriverFactory.verstappen
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.observability.instrumentationMock
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReprocessEloUseCaseShould {
  private val seasonRepository: SeasonRepository = mockk()
  private val driverRepository: DriverRepository = mockk()
  private val calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase = mockk()
  private val instrumentation: UseCaseInstrumentation = instrumentationMock()

  private val useCase: ReprocessEloUseCase =
    ReprocessEloUseCase(calculateEloOfDriversBySeasonUseCase, driverRepository, seasonRepository, instrumentation)

  @Test
  fun `reprocess elo successfully`() {
    val seasons = listOf(SeasonYear(2021), SeasonYear(2022), SeasonYear(2017), SeasonYear(2018), SeasonYear(2023))
    every { seasonRepository.findAllSeasonsYears() } returns seasons
    every { calculateEloOfDriversBySeasonUseCase(any()) } returns CalculateEloOfDriversOfBySeasonSuccess
    every { driverRepository.findAll() } returns listOf(hamilton, verstappen)
    every { driverRepository.save(any()) } just runs

    val response = useCase()

    assertThat(response).isInstanceOf(ReprocessEloSuccess::class)
    verifyOrder {
      driverRepository.findAll()
      driverRepository.save(hamilton)
      driverRepository.save(verstappen)
      seasonRepository.findAllSeasonsYears()
      calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(2017))
      calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(2018))
      calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(2021))
      calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(2022))
      calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(2023))
    }
  }

  @Test
  fun `throw EloReprocessingFailedException when reprocessing fails`() {
    val seasons = listOf(aSeason().year())
    every { seasonRepository.findAllSeasonsYears() } returns seasons
    every { calculateEloOfDriversBySeasonUseCase(any()) } returns CalculateEloOfDriversOfANonExistentSeason
    every { driverRepository.findAll() } returns listOf(hamilton, verstappen)
    every { driverRepository.save(any()) } just runs

    assertThrows<EloReprocessingFailedException> { useCase() }
    verify {
      driverRepository.findAll()
      driverRepository.save(hamilton)
      driverRepository.save(verstappen)
      seasonRepository.findAllSeasonsYears()
      calculateEloOfDriversBySeasonUseCase(any())
    }
  }
}
