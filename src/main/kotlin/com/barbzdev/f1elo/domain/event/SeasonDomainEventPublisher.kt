package com.barbzdev.f1elo.domain.event

import com.barbzdev.f1elo.domain.Season

interface SeasonDomainEventPublisher {
  fun publish(event: SeasonLoadedDomainEvent)
}

data class SeasonLoadedDomainEvent(val season: Season)
