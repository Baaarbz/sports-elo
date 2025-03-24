package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonRequest
import com.barbzdev.sportselo.formulaone.application.CalculateEloOfDriversBySeasonUseCase
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class SpringDomainEventListener(
  private val calculateEloOfDriversBySeasonUseCase: CalculateEloOfDriversBySeasonUseCase
) {
  @Async
  @EventListener
  open fun on(event: SpringSeasonLoadedDomainEvent) {
    calculateEloOfDriversBySeasonUseCase(CalculateEloOfDriversBySeasonRequest(event.season.year().value))
  }
}
