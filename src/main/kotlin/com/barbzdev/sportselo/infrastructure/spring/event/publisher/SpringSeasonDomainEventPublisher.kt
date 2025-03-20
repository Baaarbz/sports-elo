package com.barbzdev.sportselo.infrastructure.spring.event.publisher

import com.barbzdev.sportselo.domain.event.SeasonDomainEventPublisher
import com.barbzdev.sportselo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.sportselo.infrastructure.spring.event.SpringSeasonLoadedDomainEvent
import org.springframework.context.ApplicationEventPublisher

class SpringSeasonDomainEventPublisher(private val eventPublisher: ApplicationEventPublisher) :
  SeasonDomainEventPublisher {
  override fun publish(event: SeasonLoadedDomainEvent) =
    eventPublisher.publishEvent(SpringSeasonLoadedDomainEvent(event.season))
}
