package com.barbzdev.sportselo.infrastructure.spring.configuration

import com.barbzdev.sportselo.infrastructure.jpa.JpaConstructorRepository
import com.barbzdev.sportselo.infrastructure.jpa.JpaDriverRepository
import com.barbzdev.sportselo.infrastructure.jpa.JpaSeasonRepository
import com.barbzdev.sportselo.infrastructure.jpa.JpaTheoreticalPerformanceRepository
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.circuit.JpaCircuitDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor.JpaTheoreticalConstructorPerformanceDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver.JpaDriverEloHistoryDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver.JpaDriverIRatingHistoryDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.race.JpaRaceDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.race.JpaRaceResultDatasource
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.season.JpaSeasonDatasource
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
    eloHistoryDatasource: JpaDriverEloHistoryDatasource,
    iRatingHistoryDatasource: JpaDriverIRatingHistoryDatasource
  ) =
    JpaSeasonRepository(
      seasonDatasource = seasonDatasource,
      raceDatasource = raceDatasource,
      driverDatasource = driverDatasource,
      constructorDatasource = constructorDatasource,
      circuitDatasource = circuitDatasource,
      raceResultDatasource = raceResultDatasource,
      eloHistoryDatasource = eloHistoryDatasource,
      iRatingHistoryDatasource = iRatingHistoryDatasource,
    )

  @Bean
  fun jpaDriverRepository(
    driverDatasource: JpaDriverDatasource,
    eloHistoryDatasource: JpaDriverEloHistoryDatasource,
    iRatingHistoryDatasource: JpaDriverIRatingHistoryDatasource
  ) =
    JpaDriverRepository(
      driverDatasource = driverDatasource,
      eloHistoryDatasource = eloHistoryDatasource,
      iRatingHistoryDatasource = iRatingHistoryDatasource)

  @Bean
  fun jpaTheoreticalPerformanceRepository(
    theoreticalConstructorPerformanceDatasource: JpaTheoreticalConstructorPerformanceDatasource,
    constructorDatasource: JpaConstructorDatasource,
    seasonDatasource: JpaSeasonDatasource
  ) =
    JpaTheoreticalPerformanceRepository(
      theoreticalConstructorPerformanceDatasource = theoreticalConstructorPerformanceDatasource,
      constructorDatasource = constructorDatasource,
      seasonDatasource = seasonDatasource)

  @Bean
  fun jpaConstructorRepository(constructorDatasource: JpaConstructorDatasource) =
    JpaConstructorRepository(constructorDatasource)
}
