package com.barbzdev.sportselo.domain.event

import com.barbzdev.sportselo.domain.Season

fun interface SeasonDomainEventPublisher {
  fun publish(event: SeasonLoadedDomainEvent)
}

data class SeasonLoadedDomainEvent(val season: Season)
