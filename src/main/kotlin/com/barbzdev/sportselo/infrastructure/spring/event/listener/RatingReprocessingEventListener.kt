package com.barbzdev.sportselo.infrastructure.spring.event.listener

import com.barbzdev.sportselo.application.data.ReprocessEloUseCase
import com.barbzdev.sportselo.application.data.ReprocessIRatingUseCase
import com.barbzdev.sportselo.infrastructure.spring.event.EloReprocessingEvent
import com.barbzdev.sportselo.infrastructure.spring.event.IRatingReprocessingEvent
import com.barbzdev.sportselo.infrastructure.spring.event.RatingReprocessingEvent
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
