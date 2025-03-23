package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

import com.barbzdev.sportselo.formulaone.domain.event.SeasonDomainEventPublisher
import com.barbzdev.sportselo.formulaone.domain.event.SeasonLoadedDomainEvent
import org.springframework.context.ApplicationEventPublisher

class SpringSeasonDomainEventPublisher(private val eventPublisher: ApplicationEventPublisher) :
  SeasonDomainEventPublisher {
  override fun publish(event: SeasonLoadedDomainEvent) =
    eventPublisher.publishEvent(SpringSeasonLoadedDomainEvent(event.season))
}
