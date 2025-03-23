package com.barbzdev.sportselo.formulaone.infrastructure.framework.configuration

import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.core.domain.service.EloCalculator
import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.sportselo.formulaone.application.ReplicateSeasonByYearUseCase
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdUseCase
import com.barbzdev.sportselo.formulaone.application.ListingDriversUseCase
import com.barbzdev.sportselo.formulaone.application.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.domain.event.SeasonDomainEventPublisher
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.F1Repository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
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
    ReplicateSeasonByYearUseCase(
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
  fun reprocessEloUseCase(
    calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase,
    driverRepository: DriverRepository,
    seasonRepository: SeasonRepository,
    instrumentation: UseCaseInstrumentation
  ) = ReprocessEloUseCase(calculateEloOfDriversBySeasonUseCase, driverRepository, seasonRepository, instrumentation)
}
