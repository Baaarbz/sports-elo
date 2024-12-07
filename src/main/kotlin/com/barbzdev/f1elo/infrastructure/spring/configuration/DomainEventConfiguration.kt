package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.data.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.f1elo.infrastructure.spring.event.listener.SpringDomainEventListener
import com.barbzdev.f1elo.infrastructure.spring.event.publisher.SpringSeasonDomainEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainEventConfiguration {

  @Bean
  fun seasonDomainEventPublisher(applicationEventPublisher: ApplicationEventPublisher) =
    SpringSeasonDomainEventPublisher(applicationEventPublisher)

  @Bean
  fun seasonDomainEventListener(calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase) =
    SpringDomainEventListener(calculateEloOfDriversBySeasonUseCase)
}
