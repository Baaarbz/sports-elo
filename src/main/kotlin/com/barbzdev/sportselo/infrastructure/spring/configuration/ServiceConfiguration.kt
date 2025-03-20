package com.barbzdev.sportselo.infrastructure.spring.configuration

import com.barbzdev.sportselo.domain.service.EloCalculator
import com.barbzdev.sportselo.domain.service.IRatingCalculator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {
  @Bean fun eloCalculator() = EloCalculator()

  @Bean fun iRatingCalculator() = IRatingCalculator()
}
