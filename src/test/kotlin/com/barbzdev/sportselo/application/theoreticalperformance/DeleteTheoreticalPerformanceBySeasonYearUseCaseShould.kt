package com.barbzdev.sportselo.application.theoreticalperformance

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.domain.common.SeasonYear
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.sportselo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteTheoreticalPerformanceBySeasonYearUseCaseShould {
  private val theoreticalPerformanceRepository: TheoreticalPerformanceRepository = mockk(relaxed = true)
  private val service =
    DeleteTheoreticalPerformanceBySeasonYearUseCase(
      instrumentation = instrumentationMock(), repository = theoreticalPerformanceRepository)

  @Test
  fun `delete theoretical performance successfully`() {
    val request = DeleteTheoreticalPerformanceBySeasonYearRequest(2021)

    val response = service(request)

    assertThat(response).isInstanceOf(DeleteTheoreticalPerformanceBySeasonYearSuccess::class)
    verify { theoreticalPerformanceRepository.deleteBy(SeasonYear(2021)) }
  }

  @Test
  fun `throw exception when repository fails`() {
    val request = DeleteTheoreticalPerformanceBySeasonYearRequest(2021)
    every { theoreticalPerformanceRepository.deleteBy(SeasonYear(2021)) } throws RuntimeException()

    assertThrows<RuntimeException> { service(request) }
    verify { theoreticalPerformanceRepository.deleteBy(SeasonYear(2021)) }
  }
}
