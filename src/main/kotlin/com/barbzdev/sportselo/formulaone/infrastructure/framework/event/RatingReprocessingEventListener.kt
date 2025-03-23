package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

import com.barbzdev.sportselo.formulaone.application.ReprocessEloUseCase
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class RatingReprocessingEventListener(private val reprocessEloUseCase: ReprocessEloUseCase) {

  @Async
  @EventListener
  open fun on(event: RatingReprocessingEvent) {
    when (event) {
      is EloReprocessingEvent -> reprocessEloUseCase()
    }
  }
}
