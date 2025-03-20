package com.barbzdev.sportselo.infrastructure.spring.configuration

import com.barbzdev.sportselo.application.data.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.sportselo.application.data.CalculateIRatingOfDriversBySeasonUseCase
import com.barbzdev.sportselo.infrastructure.spring.event.listener.SpringDomainEventListener
import com.barbzdev.sportselo.infrastructure.spring.event.publisher.SpringSeasonDomainEventPublisher
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
