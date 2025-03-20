package com.barbzdev.sportselo.infrastructure.spring.event.listener

import com.barbzdev.sportselo.formulaone.application.data.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.application.data.ReprocessIRatingUseCase
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.IRatingReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.RatingReprocessingEventListener
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
