package com.barbzdev.f1elo.application.data

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.domain.service.EloCalculator
import com.barbzdev.f1elo.factory.DriverFactory.hamilton
import com.barbzdev.f1elo.factory.DriverFactory.verstappen
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.observability.instrumentationMock
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
