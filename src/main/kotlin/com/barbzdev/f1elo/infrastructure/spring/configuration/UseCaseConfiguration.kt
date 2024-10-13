package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.GatherRacesBySeasonUseCase
import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.common.Season
import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.domain.repository.F1Repository
import com.barbzdev.f1elo.domain.repository.RaceRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {

  val mockF1Repository = object : F1Repository {
    override fun gatherRacesBySeason(season: Season): List<Race> = TODO("Not yet implemented")
    override fun getLastSeasonLoaded(): Season = TODO("Not yet implemented")
  }

  val mockRaceRepository = object : RaceRepository {
    override fun saveAll(races: List<Race>) = TODO("Not yet implemented")
  }

  val mockSeasonDomainEventPublisher = object : SeasonDomainEventPublisher {
    override fun publish(event: SeasonLoadedDomainEvent) = TODO("Not yet implemented")
  }

  @Bean
  fun gatherRaceResultUseCase() = GatherRacesBySeasonUseCase(
    mockF1Repository,
    mockRaceRepository,
    mockSeasonDomainEventPublisher
  )
}
