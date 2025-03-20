package com.barbzdev.sportselo.formulaone.infrastructure.framework.configuration

import com.barbzdev.sportselo.core.domain.service.EloCalculator
import com.barbzdev.sportselo.domain.service.IRatingCalculator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {
  @Bean fun eloCalculator() = EloCalculator()

  @Bean fun iRatingCalculator() = IRatingCalculator()
}
