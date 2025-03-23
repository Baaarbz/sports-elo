package com.barbzdev.sportselo.formulaone.infrastructure.spring.event.listener

import com.barbzdev.sportselo.formulaone.application.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEventListener
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class EloReprocessingEventListenerShould {
  private val reprocessEloUseCase: ReprocessEloUseCase = mockk(relaxed = true)

  private val eloReprocessingEventListener = EloReprocessingEventListener(reprocessEloUseCase)

  @Test
  fun `reprocess elo when elo reprocessing event is received`() {
    eloReprocessingEventListener.on(EloReprocessingEvent)

    verify { reprocessEloUseCase() }
  }
}
