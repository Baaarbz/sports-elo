package com.barbzdev.sportselo.formulaone.infrastructure.framework.configuration

import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.CircuitMapper
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.ConstructorMapper
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.DriverMapper
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.SeasonMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfiguration {
  @Bean
  fun jpaSeasonRepository(seasonDatasource: JpaSeasonDatasource, seasonMapper: SeasonMapper) =
    JpaSeasonRepository(seasonDatasource, seasonMapper)

  @Bean
  fun jpaDriverRepository(driverDatasource: JpaDriverDatasource, driverMapper: DriverMapper) =
    JpaDriverRepository(driverDatasource, driverMapper)

  @Bean fun driverMapper() = DriverMapper()

  @Bean
  fun seasonMapper(circuitMapper: CircuitMapper, driverMapper: DriverMapper, constructorMapper: ConstructorMapper) =
    SeasonMapper(circuitMapper, driverMapper, constructorMapper)

  @Bean fun circuitMapper() = CircuitMapper()

  @Bean fun constructorMapper() = ConstructorMapper()
}
