package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.infrastructure.jpa.JpaSeasonRepository
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaCircuitDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaConstructorDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverEloHistoryDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaRaceDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaRaceResultDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaRepositoryConfiguration {
  @Bean
  fun jpaSeasonRepository(
    seasonDatasource: JpaSeasonDatasource,
    raceDatasource: JpaRaceDatasource,
    driverDatasource: JpaDriverDatasource,
    constructorDatasource: JpaConstructorDatasource,
    circuitDatasource: JpaCircuitDatasource,
    raceResultDatasource: JpaRaceResultDatasource,
    eloHistoryDatasource: JpaDriverEloHistoryDatasource
  ) = JpaSeasonRepository(
    seasonDatasource = seasonDatasource,
    raceDatasource = raceDatasource,
    driverDatasource = driverDatasource,
    constructorDatasource = constructorDatasource,
    circuitDatasource = circuitDatasource,
    raceResultDatasource = raceResultDatasource,
    eloHistoryDatasource = eloHistoryDatasource
  )
}
