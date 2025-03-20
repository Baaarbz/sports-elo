package com.barbzdev.sportselo.application.data

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.exception.IRatingReprocessingFailedException
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.DriverRepository
import com.barbzdev.sportselo.domain.repository.SeasonRepository
import com.barbzdev.sportselo.factory.DriverFactory.hamilton
import com.barbzdev.sportselo.factory.DriverFactory.verstappen
import com.barbzdev.sportselo.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.observability.instrumentationMock
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReprocessIRatingUseCaseShould {
  private val seasonRepository: SeasonRepository = mockk()
  private val driverRepository: DriverRepository = mockk()
  private val calculateIRatingOfDriversBySeasonUseCase: CalculateIRatingOfDriversBySeasonUseCase = mockk()
  private val instrumentation: UseCaseInstrumentation = instrumentationMock()

  private val useCase: ReprocessIRatingUseCase =
    ReprocessIRatingUseCase(
      calculateIRatingOfDriversBySeasonUseCase, driverRepository, seasonRepository, instrumentation)

  @Test
  fun `reprocess iRating successfully`() {
    val seasons = listOf(SeasonYear(2021), SeasonYear(2022), SeasonYear(2017), SeasonYear(2018), SeasonYear(2023))
    every { seasonRepository.findAllSeasonsYears() } returns seasons
    every { calculateIRatingOfDriversBySeasonUseCase(any()) } returns CalculateIRatingOfDriversOfBySeasonSuccess
    every { driverRepository.findAll() } returns listOf(hamilton, verstappen)
    every { driverRepository.save(any()) } just runs

    val response = useCase()

    assertThat(response).isInstanceOf(ReprocessIRatingSuccess::class)
    verifyOrder {
      driverRepository.findAll()
      driverRepository.save(any())
      seasonRepository.findAllSeasonsYears()
      calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(2017))
      calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(2018))
      calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(2021))
      calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(2022))
      calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(2023))
    }
  }

  @Test
  fun `throw IRatingReprocessingFailedException when reprocessing fails`() {
    val seasons = listOf(aSeason().year())
    every { seasonRepository.findAllSeasonsYears() } returns seasons
    every { calculateIRatingOfDriversBySeasonUseCase(any()) } returns CalculateIRatingOfDriversOfANonExistentSeason
    every { driverRepository.findAll() } returns listOf(hamilton, verstappen)
    every { driverRepository.save(any()) } just runs

    assertThrows<IRatingReprocessingFailedException> { useCase() }
    verify {
      driverRepository.findAll()
      driverRepository.save(any())
      seasonRepository.findAllSeasonsYears()
      calculateIRatingOfDriversBySeasonUseCase(any())
    }
  }
}
