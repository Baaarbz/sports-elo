package com.barbzdev.f1elo.infrastructure.spring.event

import com.barbzdev.f1elo.domain.Season
import org.springframework.context.ApplicationEvent

data class SpringSeasonLoadedDomainEvent(val season: Season) : ApplicationEvent(season)
