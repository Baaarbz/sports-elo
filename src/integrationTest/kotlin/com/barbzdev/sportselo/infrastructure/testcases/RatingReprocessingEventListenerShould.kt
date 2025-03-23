package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.formulaone.application.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.RatingReprocessingEventListener
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher

abstract class RatingReprocessingEventListenerShould : IntegrationTestConfiguration() {

  @MockkBean(relaxed = true) private lateinit var reprocessEloUseCase: ReprocessEloUseCase

  @Autowired private lateinit var eventPublisher: ApplicationEventPublisher

  @Test
  fun `handle reprocess ELO event`() {
    eventPublisher.publishEvent(EloReprocessingEvent)

    verify { reprocessEloUseCase.invoke() }
  }
}
