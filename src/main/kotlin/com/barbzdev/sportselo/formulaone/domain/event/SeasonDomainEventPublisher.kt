package com.barbzdev.sportselo.formulaone.domain.event

import com.barbzdev.sportselo.formulaone.domain.Season

fun interface SeasonDomainEventPublisher {
  fun publish(event: SeasonLoadedDomainEvent)
}

data class SeasonLoadedDomainEvent(val season: Season)
