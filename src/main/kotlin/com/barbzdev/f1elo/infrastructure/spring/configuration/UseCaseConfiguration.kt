package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.f1elo.application.GatherRacesBySeasonUseCase
import com.barbzdev.f1elo.application.ListingDriversUseCase
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
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
  fun listingDriversUseCase(driverRepository: DriverRepository, useCaseInstrumentation: UseCaseInstrumentation) = ListingDriversUseCase(driverRepository, useCaseInstrumentation)
}
