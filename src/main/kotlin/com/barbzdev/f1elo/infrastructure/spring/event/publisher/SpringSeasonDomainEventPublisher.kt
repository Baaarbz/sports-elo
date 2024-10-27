package com.barbzdev.f1elo.infrastructure.spring.event.publisher

import com.barbzdev.f1elo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.infrastructure.spring.event.SpringSeasonLoadedDomainEvent
import org.springframework.context.ApplicationEventPublisher

class SpringSeasonDomainEventPublisher(private val eventPublisher: ApplicationEventPublisher) :
  SeasonDomainEventPublisher {
  override fun publish(event: SeasonLoadedDomainEvent) =
    eventPublisher.publishEvent(SpringSeasonLoadedDomainEvent(event.season))
}
