package com.barbzdev.f1elo.infrastructure.spring.event.listener

import com.barbzdev.f1elo.application.data.CalculateEloOfDriversBySeasonRequest
import com.barbzdev.f1elo.application.data.CalculateEloOfDriversBySeasonUseCase
import com.barbzdev.f1elo.application.data.CalculateIRatingOfDriversBySeasonRequest
import com.barbzdev.f1elo.application.data.CalculateIRatingOfDriversBySeasonUseCase
import com.barbzdev.f1elo.infrastructure.spring.event.SpringSeasonLoadedDomainEvent
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
