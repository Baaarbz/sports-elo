package com.barbzdev.sportselo.formulaone.infrastructure.framework.configuration

import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.sportselo.formulaone.application.data.CalculateIRatingOfDriversBySeasonUseCase
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.SpringDomainEventListener
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.SpringSeasonDomainEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainEventConfiguration {

  @Bean
  fun seasonDomainEventPublisher(applicationEventPublisher: ApplicationEventPublisher) =
    SpringSeasonDomainEventPublisher(applicationEventPublisher)

  @Bean
  fun seasonDomainEventListener(
      calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase,
      calculateIRatingOfDriversBySeasonUseCase: CalculateIRatingOfDriversBySeasonUseCase
  ) = SpringDomainEventListener(calculateEloOfDriversBySeasonUseCase, calculateIRatingOfDriversBySeasonUseCase)
}
