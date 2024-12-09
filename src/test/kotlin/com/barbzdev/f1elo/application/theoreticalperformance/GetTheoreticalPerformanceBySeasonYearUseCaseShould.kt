package com.barbzdev.f1elo.application.theoreticalperformance

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.f1elo.factory.TheoreticalPerformanceFactory.aTheoreticalPerformance
import com.barbzdev.f1elo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetTheoreticalPerformanceBySeasonYearUseCaseShould {
  private val theoreticalPerformanceRepository: TheoreticalPerformanceRepository = mockk()
  private val service =
    GetTheoreticalPerformanceBySeasonYearUseCase(
      instrumentation = instrumentationMock(), repository = theoreticalPerformanceRepository)

  @Test
  fun `return success when theoretical performance is found`() {
    val request = GetTheoreticalPerformanceBySeasonYearRequest(2021)
    every { theoreticalPerformanceRepository.findBy(SeasonYear(2021)) } returns aTheoreticalPerformance()

    val response = service(request)

    assertThat(response).isInstanceOf(GetTheoreticalPerformanceBySeasonYearSuccess::class)
    verify { theoreticalPerformanceRepository.findBy(SeasonYear(2021)) }
  }

  @Test
  fun `return not found when theoretical performance is not found`() {
    val request = GetTheoreticalPerformanceBySeasonYearRequest(2021)
    every { theoreticalPerformanceRepository.findBy(SeasonYear(2021)) } returns null

    val response = service(request)

    assertThat(response).isInstanceOf(GetTheoreticalPerformanceBySeasonYearNotFound::class)
    verify { theoreticalPerformanceRepository.findBy(SeasonYear(2021)) }
  }

  @Test
  fun `throw exception when repository fails`() {
    val request = GetTheoreticalPerformanceBySeasonYearRequest(2021)
    every { theoreticalPerformanceRepository.findBy(SeasonYear(2021)) } throws RuntimeException()

    assertThrows<RuntimeException> { service(request) }
    verify { theoreticalPerformanceRepository.findBy(SeasonYear(2021)) }
  }
}
