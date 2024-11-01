package com.barbzdev.f1elo.infrastructure.spring.event.listener

import com.barbzdev.f1elo.application.CalculateEloOfDriversBySeasonRequest
import com.barbzdev.f1elo.application.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.f1elo.infrastructure.spring.event.SpringSeasonLoadedDomainEvent
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
