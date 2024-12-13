package com.barbzdev.f1elo.infrastructure.spring.event.listener

import com.barbzdev.f1elo.application.data.ReprocessEloUseCase
import com.barbzdev.f1elo.application.data.ReprocessIRatingUseCase
import com.barbzdev.f1elo.infrastructure.spring.event.EloReprocessingEvent
import com.barbzdev.f1elo.infrastructure.spring.event.IRatingReprocessingEvent
import com.barbzdev.f1elo.infrastructure.spring.event.RatingReprocessingEvent
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
