package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.f1elo.application.GatherRacesBySeasonUseCase
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {
  @Bean
  fun gatherRaceResultUseCase(
    f1Repository: F1Repository,
    seasonRepository: SeasonRepository,
    driverRepository: DriverRepository,
    seasonDomainEventPublisher: SeasonDomainEventPublisher
  ) = GatherRacesBySeasonUseCase(f1Repository, seasonRepository, driverRepository, seasonDomainEventPublisher)

  @Bean
  fun calculateEloOfDriversBySeasonUseCase(seasonRepository: SeasonRepository, driverRepository: DriverRepository) =
    CalculateEloOfDriversBySeasonUseCase(seasonRepository, driverRepository)
}
