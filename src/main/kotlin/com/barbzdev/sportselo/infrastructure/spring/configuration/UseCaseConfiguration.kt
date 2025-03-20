package com.barbzdev.sportselo.infrastructure.spring.configuration

import com.barbzdev.sportselo.application.data.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.sportselo.application.data.CalculateIRatingOfDriversBySeasonUseCase
import com.barbzdev.sportselo.application.data.GatherRacesBySeasonUseCase
import com.barbzdev.sportselo.application.data.ReprocessEloUseCase
import com.barbzdev.sportselo.application.data.ReprocessIRatingUseCase
import com.barbzdev.sportselo.application.driver.GetDriverByIdUseCase
import com.barbzdev.sportselo.application.driver.ListingDriversUseCase
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceUseCase
import com.barbzdev.sportselo.application.theoreticalperformance.DeleteTheoreticalPerformanceBySeasonYearUseCase
import com.barbzdev.sportselo.application.theoreticalperformance.GetTheoreticalPerformanceBySeasonYearUseCase
import com.barbzdev.sportselo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.ConstructorRepository
import com.barbzdev.sportselo.domain.repository.DriverRepository
import com.barbzdev.sportselo.domain.repository.F1Repository
import com.barbzdev.sportselo.domain.repository.SeasonRepository
import com.barbzdev.sportselo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.sportselo.domain.service.EloCalculator
import com.barbzdev.sportselo.domain.service.IRatingCalculator
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
    calculateIRatingOfDriversBySeasonUseCase: CalculateIRatingOfDriversBySeasonUseCase,
    driverRepository: DriverRepository,
    seasonRepository: SeasonRepository,
    instrumentation: UseCaseInstrumentation
  ) =
    ReprocessIRatingUseCase(
      calculateIRatingOfDriversBySeasonUseCase, driverRepository, seasonRepository, instrumentation)

  @Bean
  fun calculateIRatingOfDriversBySeasonUseCase(
    seasonRepository: SeasonRepository,
    driverRepository: DriverRepository,
    iRatingCalculator: IRatingCalculator,
    theoreticalPerformanceRepository: TheoreticalPerformanceRepository,
    instrumentation: UseCaseInstrumentation
  ) =
    CalculateIRatingOfDriversBySeasonUseCase(
      seasonRepository, driverRepository, iRatingCalculator, theoreticalPerformanceRepository, instrumentation)
}
