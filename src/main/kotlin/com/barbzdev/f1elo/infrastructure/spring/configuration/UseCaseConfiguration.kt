package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.data.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.f1elo.application.data.GatherRacesBySeasonUseCase
import com.barbzdev.f1elo.application.data.ReprocessEloUseCase
import com.barbzdev.f1elo.application.data.ReprocessIRatingUseCase
import com.barbzdev.f1elo.application.driver.GetDriverByIdUseCase
import com.barbzdev.f1elo.application.driver.ListingDriversUseCase
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceUseCase
import com.barbzdev.f1elo.application.theoreticalperformance.DeleteTheoreticalPerformanceBySeasonYearUseCase
import com.barbzdev.f1elo.application.theoreticalperformance.GetTheoreticalPerformanceBySeasonYearUseCase
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.ConstructorRepository
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.f1elo.domain.service.EloCalculator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {
  @Bean
  fun gatherRaceResultUseCase(
    f1Repository: F1Repository,
    seasonRepository: SeasonRepository,
    driverRepository: DriverRepository,
    seasonDomainEventPublisher: SeasonDomainEventPublisher,
    useCaseInstrumentation: UseCaseInstrumentation
  ) =
    GatherRacesBySeasonUseCase(
      f1Repository, seasonRepository, driverRepository, seasonDomainEventPublisher, useCaseInstrumentation)

  @Bean
  fun calculateEloOfDriversBySeasonUseCase(
    seasonRepository: SeasonRepository,
    driverRepository: DriverRepository,
    eloCalculator: EloCalculator,
    useCaseInstrumentation: UseCaseInstrumentation
  ) = CalculateEloOfDriversBySeasonUseCase(seasonRepository, driverRepository, eloCalculator, useCaseInstrumentation)

  @Bean
  fun listingDriversUseCase(driverRepository: DriverRepository, useCaseInstrumentation: UseCaseInstrumentation) =
    ListingDriversUseCase(driverRepository, useCaseInstrumentation)

  @Bean
  fun getDriverByIdUseCase(driverRepository: DriverRepository, useCaseInstrumentation: UseCaseInstrumentation) =
    GetDriverByIdUseCase(driverRepository, useCaseInstrumentation)

  @Bean
  fun addTheoreticalPerformanceUseCase(
    instrumentation: UseCaseInstrumentation,
    theoreticalPerformanceRepository: TheoreticalPerformanceRepository,
    seasonRepository: SeasonRepository,
    constructorRepository: ConstructorRepository
  ) =
    AddTheoreticalPerformanceUseCase(
      instrumentation, theoreticalPerformanceRepository, seasonRepository, constructorRepository)

  @Bean
  fun deleteTheoreticalPerformanceBySeasonYearUseCase(
    instrumentation: UseCaseInstrumentation,
    theoreticalPerformanceRepository: TheoreticalPerformanceRepository
  ) = DeleteTheoreticalPerformanceBySeasonYearUseCase(instrumentation, theoreticalPerformanceRepository)

  @Bean
  fun getTheoreticalPerformanceBySeasonYearUseCase(
    instrumentation: UseCaseInstrumentation,
    theoreticalPerformanceRepository: TheoreticalPerformanceRepository
  ) = GetTheoreticalPerformanceBySeasonYearUseCase(instrumentation, theoreticalPerformanceRepository)

  @Bean
  fun reprocessEloUseCase(
    calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase,
    driverRepository: DriverRepository,
    seasonRepository: SeasonRepository,
    instrumentation: UseCaseInstrumentation
  ) = ReprocessEloUseCase(calculateEloOfDriversBySeasonUseCase, driverRepository, seasonRepository, instrumentation)

  @Bean
  fun reprocessIRatingUseCase(
    driverRepository: DriverRepository,
    seasonRepository: SeasonRepository,
    instrumentation: UseCaseInstrumentation
  ) = ReprocessIRatingUseCase()
}
