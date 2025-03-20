package com.barbzdev.sportselo.infrastructure.spring.event

import com.barbzdev.sportselo.domain.Season
import org.springframework.context.ApplicationEvent

data class SpringSeasonLoadedDomainEvent(val season: Season) : ApplicationEvent(season)
