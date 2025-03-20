package com.barbzdev.sportselo.infrastructure.spring.controller.data

import com.barbzdev.sportselo.formulaone.infrastructure.framework.controller.data.DataController
import com.barbzdev.sportselo.formulaone.infrastructure.framework.controller.data.HttpReprocessRatingsRequest
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.IRatingReprocessingEvent
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

class DataControllerShould {
  private val eventPublisher: ApplicationEventPublisher = mockk(relaxed = true)

  private val dataController = DataController(eventPublisher)

  @Test
  fun `start ratings reprocessing`() {
    val body = HttpReprocessRatingsRequest(iRating = true, elo = true)

    val response = dataController.startRatingsReprocessing(body)

    assertEquals(202, response.statusCode.value())
    verify { eventPublisher.publishEvent(IRatingReprocessingEvent) }
    verify { eventPublisher.publishEvent(EloReprocessingEvent) }
  }
}
