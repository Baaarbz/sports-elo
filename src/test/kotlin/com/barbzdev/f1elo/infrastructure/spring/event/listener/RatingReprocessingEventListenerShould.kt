package com.barbzdev.f1elo.infrastructure.spring.event.listener

import com.barbzdev.f1elo.application.data.ReprocessEloUseCase
import com.barbzdev.f1elo.application.data.ReprocessIRatingUseCase
import com.barbzdev.f1elo.infrastructure.spring.event.EloReprocessingEvent
import com.barbzdev.f1elo.infrastructure.spring.event.IRatingReprocessingEvent
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class RatingReprocessingEventListenerShould {
  private val reprocessEloUseCase: ReprocessEloUseCase = mockk(relaxed = true)
  private val reprocessIRatingUseCase: ReprocessIRatingUseCase = mockk(relaxed = true)

  private val ratingReprocessingEventListener =
    RatingReprocessingEventListener(reprocessEloUseCase, reprocessIRatingUseCase)

  @Test
  fun `reprocess elo when elo reprocessing event is received`() {
    ratingReprocessingEventListener.on(EloReprocessingEvent)

    verify { reprocessEloUseCase() }
    verify(exactly = 0) { reprocessIRatingUseCase() }
  }

  @Test
  fun `reprocess iRating when iRating reprocessing event is received`() {
    ratingReprocessingEventListener.on(IRatingReprocessingEvent)

    verify { reprocessIRatingUseCase() }
    verify(exactly = 0) { reprocessEloUseCase() }
  }
}
