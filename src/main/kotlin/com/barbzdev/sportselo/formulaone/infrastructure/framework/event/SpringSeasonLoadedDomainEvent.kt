package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

import com.barbzdev.sportselo.formulaone.domain.Season
import org.springframework.context.ApplicationEvent

data class SpringSeasonLoadedDomainEvent(val season: Season) : ApplicationEvent(season)
