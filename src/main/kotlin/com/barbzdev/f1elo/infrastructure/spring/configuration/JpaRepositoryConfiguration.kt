package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.infrastructure.jpa.JpaSeasonRepository
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaRepositoryConfiguration {
  @Bean fun jpaSeasonRepository(datasource: JpaSeasonDatasource) = JpaSeasonRepository(datasource)
}
