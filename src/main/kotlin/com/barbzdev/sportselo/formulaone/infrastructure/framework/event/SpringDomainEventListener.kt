package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonRequest
import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.sportselo.formulaone.application.data.CalculateIRatingOfDriversBySeasonRequest
import com.barbzdev.sportselo.formulaone.application.data.CalculateIRatingOfDriversBySeasonUseCase
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class SpringDomainEventListener(
    private val calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase,
    private val calculateIRatingOfDriversBySeasonUseCase: CalculateIRatingOfDriversBySeasonUseCase
) {
  @Async
  @EventListener
  open fun on(event: SpringSeasonLoadedDomainEvent) {
    calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(event.season.year().value))
    calculateIRatingOfDriversBySeasonUseCase(CalculateIRatingOfDriversBySeasonRequest(event.season.year().value))
  }
}
