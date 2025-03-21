package com.barbzdev.sportselo.application.data

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.core.domain.service.EloCalculator
import com.barbzdev.sportselo.factory.DriverFactory.hamilton
import com.barbzdev.sportselo.factory.DriverFactory.verstappen
import com.barbzdev.sportselo.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonRequest
import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversOfBySeasonSuccess
import com.barbzdev.sportselo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class CalculateEloOfDriversBySeasonUseCaseShould {
  private val seasonRepository: SeasonRepository = mockk()
  private val driverRepository: DriverRepository = mockk(relaxed = true)
  private val eloCalculator: EloCalculator = mockk()
  private val instrumentation: UseCaseInstrumentation = instrumentationMock<UseCaseInstrumentation>()

  private val useCase: CalculateEloOfDriversBySeasonUseCase =
    CalculateEloOfDriversBySeasonUseCase(seasonRepository, driverRepository, eloCalculator, instrumentation)

  @Test
  fun `calculate elo of drivers when season is found`() {
    val aSeason = aSeason()
    val drivers = listOf(verstappen, hamilton)
    every { seasonRepository.findBy(any()) } returns aSeason
    every { eloCalculator.calculateEloRatingsByPosition(any(), any()) } returns drivers

    val response = useCase(CalculateEloOfDriversBySeasonRequest(aSeason.year().value))

    assertThat(response).isInstanceOf(CalculateEloOfDriversOfBySeasonSuccess::class)
    verify {
      seasonRepository.findBy(aSeason.year())
      eloCalculator.calculateEloRatingsByPosition(any(), any())
      driverRepository.save(verstappen)
      driverRepository.save(hamilton)
    }
  }
}
