package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.GatherRacesBySeasonUseCase
import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.DriverId
import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {
  val mockSeasonRepository = object : SeasonRepository {
    override fun getLastSeasonLoaded(): Season = TODO("Not yet implemented")
    override fun save(season: Season) = TODO("Not yet implemented")
  }

  val mockDriverRepository = object : DriverRepository {
    override fun findBy(driver: DriverId): Driver? {
      TODO("Not yet implemented")
    }

    override fun save(driver: Driver) {
      TODO("Not yet implemented")
    }
  }

  val mockSeasonDomainEventPublisher = object : SeasonDomainEventPublisher {
    override fun publish(event: SeasonLoadedDomainEvent) = TODO("Not yet implemented")
  }

  @Bean
  fun gatherRaceResultUseCase(f1Repository: F1Repository) = GatherRacesBySeasonUseCase(
    f1Repository,
    mockSeasonRepository,
    mockDriverRepository,
    mockSeasonDomainEventPublisher
  )
}
