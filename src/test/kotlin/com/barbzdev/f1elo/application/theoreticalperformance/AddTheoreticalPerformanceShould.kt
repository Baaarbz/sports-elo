package com.barbzdev.f1elo.application.theoreticalperformance

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.ConstructorId
import com.barbzdev.f1elo.domain.ConstructorPerformance
import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.repository.ConstructorRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.f1elo.factory.ConstructorFactory.aConstructor
import com.barbzdev.f1elo.factory.ConstructorFactory.ferrariConstructor
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.factory.TheoreticalPerformanceFactory.aTheoreticalPerformance
import com.barbzdev.f1elo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AddTheoreticalPerformanceShould {
  private val theoreticalPerformanceRepository: TheoreticalPerformanceRepository = mockk()
  private val seasonRepository: SeasonRepository = mockk()
  private val constructorRepository: ConstructorRepository = mockk()

  private val service =
    AddTheoreticalPerformanceUseCase(
      instrumentation = instrumentationMock(),
      theoreticalPerformanceRepository = theoreticalPerformanceRepository,
      seasonRepository = seasonRepository,
      constructorRepository = constructorRepository)

  @Test
  fun `add theoretical performance to the database`() {
    val request = A_REQUEST
    every { seasonRepository.getSeasonIdBy(any()) } returns aSeason().id()
    every { constructorRepository.findBy(any()) } returns ferrariConstructor
    every { theoreticalPerformanceRepository.findBy(any()) } returns null

    val response = service(request)

    assertThat(response).isInstanceOf(AddTheoreticalPerformanceSuccess::class)
    verify {
      seasonRepository.getSeasonIdBy(SeasonYear(2021))
      theoreticalPerformanceRepository.findBy(SeasonYear(2021))
      constructorRepository.findBy(ConstructorId("ferrari"))
      theoreticalPerformanceRepository.save(
        TheoreticalPerformance.create(
          seasonYear = 2021,
          isAnalyzedSeason = true,
          constructorsPerformance = listOf(ConstructorPerformance(aConstructor(), 0.0f))))
    }
  }

  @Test
  fun `return AddTheoreticalPerformanceOverAnInvalidConstructor when constructor is not found`() {
    val request = A_REQUEST
    every { seasonRepository.getSeasonIdBy(any()) } returns aSeason().id()
    every { theoreticalPerformanceRepository.findBy(any()) } returns null
    every { constructorRepository.findBy(any()) } returns null

    val response = service(request)

    assertThat(response).isInstanceOf(AddTheoreticalPerformanceOverAnInvalidConstructor::class)
    verify {
      seasonRepository.getSeasonIdBy(SeasonYear(2021))
      theoreticalPerformanceRepository.findBy(SeasonYear(2021))
      constructorRepository.findBy(ConstructorId("ferrari"))
    }
    verify(exactly = 0) { theoreticalPerformanceRepository.save(any()) }
  }

  @Test
  fun `return AddTheoreticalPerformanceOverANonExistentSeason when season is not found`() {
    val request = A_REQUEST
    every { seasonRepository.getSeasonIdBy(any()) } returns null

    val response = service(request)

    assertThat(response).isInstanceOf(AddTheoreticalPerformanceOverANonExistentSeason::class)
    verify { seasonRepository.getSeasonIdBy(SeasonYear(2021)) }
    verify(exactly = 0) { constructorRepository.findBy(any()) }
    verify(exactly = 0) { theoreticalPerformanceRepository.save(any()) }
    verify(exactly = 0) { theoreticalPerformanceRepository.findBy(any()) }
  }

  @Test
  fun `return AddTheoreticalPerformanceAlreadyCreated when theoretical performance already exists`() {
    val request = A_REQUEST
    every { seasonRepository.getSeasonIdBy(any()) } returns aSeason().id()
    every { theoreticalPerformanceRepository.findBy(any()) } returns aTheoreticalPerformance()

    val response = service(request)

    assertThat(response).isInstanceOf(AddTheoreticalPerformanceAlreadyCreated::class)
    verify {
      seasonRepository.getSeasonIdBy(SeasonYear(2021))
      theoreticalPerformanceRepository.findBy(SeasonYear(2021))
    }
    verify(exactly = 0) { constructorRepository.findBy(any()) }
    verify(exactly = 0) { theoreticalPerformanceRepository.save(any()) }
  }

  @Test
  fun `return AddTheoreticalPerformanceOverAnInvalidPerformance when performance is invalid`() {
    val request =
      A_REQUEST.copy(
        theoreticalConstructorPerformances =
          listOf(AddTheoreticalPerformanceConstructorPerformance(constructorId = "ferrari", performance = -1.1f)))
    every { seasonRepository.getSeasonIdBy(any()) } returns aSeason().id()
    every { theoreticalPerformanceRepository.findBy(any()) } returns null
    every { constructorRepository.findBy(any()) } returns ferrariConstructor

    val response = service(request)

    assertThat(response).isInstanceOf(AddTheoreticalPerformanceOverAnInvalidPerformance::class)
    verify {
      seasonRepository.getSeasonIdBy(SeasonYear(2021))
      theoreticalPerformanceRepository.findBy(SeasonYear(2021))
      constructorRepository.findBy(ConstructorId("ferrari"))
    }
    verify(exactly = 0) { theoreticalPerformanceRepository.save(any()) }
  }

  private companion object {
    val A_REQUEST =
      AddTheoreticalPerformanceRequest(
        seasonYear = 2021,
        isAnalyzedData = true,
        theoreticalConstructorPerformances =
          listOf(AddTheoreticalPerformanceConstructorPerformance(constructorId = "ferrari", performance = 0.0f)))
  }
}
