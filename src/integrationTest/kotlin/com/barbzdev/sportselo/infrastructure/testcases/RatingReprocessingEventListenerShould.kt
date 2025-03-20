package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.formulaone.application.data.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.application.data.ReprocessIRatingUseCase
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.IRatingReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.RatingReprocessingEventListener
import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher

abstract class RatingReprocessingEventListenerShould : IntegrationTestConfiguration() {

  @MockkBean(relaxed = true) private lateinit var reprocessIRatingUseCase: ReprocessIRatingUseCase
  @MockkBean(relaxed = true) private lateinit var reprocessEloUseCase: ReprocessEloUseCase

  @Autowired private lateinit var eventPublisher: ApplicationEventPublisher
  @Autowired private lateinit var reprocessingEventListener: RatingReprocessingEventListener

  @Test
  fun `handle reprocess iRating event`() {
    eventPublisher.publishEvent(IRatingReprocessingEvent)

    verify { reprocessIRatingUseCase.invoke() }
  }

  @Test
  fun `handle reprocess ELO event`() {
    eventPublisher.publishEvent(EloReprocessingEvent)

    verify { reprocessEloUseCase.invoke() }
  }
}
