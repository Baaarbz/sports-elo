package com.barbzdev.sportselo.application.data

import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.DriverRepository
import com.barbzdev.sportselo.domain.repository.SeasonRepository
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.sportselo.domain.service.IRatingCalculator
import com.barbzdev.sportselo.factory.DriverFactory.hamilton
import com.barbzdev.sportselo.factory.DriverFactory.verstappen
import com.barbzdev.sportselo.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.factory.TheoreticalPerformanceFactory.aTheoreticalPerformance
import com.barbzdev.sportselo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CalculateIRatingOfDriversBySeasonUseCaseShould {
  private val seasonRepository: SeasonRepository = mockk()
  private val driverRepository: DriverRepository = mockk(relaxed = true)
  private val iRatingCalculator: IRatingCalculator = mockk()
  private val theoreticalPerformanceRepository: TheoreticalPerformanceRepository = mockk()
  private val instrumentation: UseCaseInstrumentation = instrumentationMock()

  private val useCase: CalculateIRatingOfDriversBySeasonUseCase =
    CalculateIRatingOfDriversBySeasonUseCase(
      seasonRepository, driverRepository, iRatingCalculator, theoreticalPerformanceRepository, instrumentation)

  @Test
  fun `calculate iRating of drivers when season is found`() {
    val aSeason = aSeason()
    val drivers = listOf(verstappen, hamilton)
    every { seasonRepository.findBy(any()) } returns aSeason
    every { iRatingCalculator.calculateSOF(any()) } returns 1500.0
    every { theoreticalPerformanceRepository.findBy(any()) } returns aTheoreticalPerformance()
    every { iRatingCalculator.calculateIRatingDelta(any(), any(), any(), any(), any()) } returns 10
    every { driverRepository.findBy(any()) } returnsMany drivers

    val response = useCase(CalculateIRatingOfDriversBySeasonRequest(aSeason.year().value))

    assertThat(response).isInstanceOf(CalculateIRatingOfDriversOfBySeasonSuccess::class.java)
    verify {
      seasonRepository.findBy(aSeason.year())
      iRatingCalculator.calculateSOF(drivers.map { it.currentIRating() })
      theoreticalPerformanceRepository.findBy(aSeason.year())
      iRatingCalculator.calculateIRatingDelta(any(), any(), any(), any(), any())
      driverRepository.save(any())
    }
  }
}
