package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

import com.barbzdev.sportselo.formulaone.application.data.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.application.data.ReprocessIRatingUseCase
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class RatingReprocessingEventListener(
  private val reprocessEloUseCase: ReprocessEloUseCase,
  private val reprocessIRatingUseCase: ReprocessIRatingUseCase
) {

  @Async
  @EventListener
  open fun on(event: RatingReprocessingEvent) {
    when (event) {
      is EloReprocessingEvent -> reprocessEloUseCase()
      is IRatingReprocessingEvent -> reprocessIRatingUseCase()
    }
  }
}
