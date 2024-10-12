package com.barbzdev.f1elo.domain.event

import com.barbzdev.f1elo.domain.common.Season

interface SeasonDomainEventPublisher {
  fun publish(event: SeasonLoadedDomainEvent)
}

data class SeasonLoadedDomainEvent(val season: Season)
