package com.barbzdev.sportselo.core.infrastructure.data

import com.barbzdev.sportselo.core.infrastructure.framework.controller.data.DataController
import com.barbzdev.sportselo.core.infrastructure.framework.controller.data.HttpReprocessEloRequest
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

class DataControllerShould {
  private val eventPublisher: ApplicationEventPublisher = mockk(relaxed = true)

  private val dataController = DataController(eventPublisher)

  @Test
  fun `start elo reprocessing`() {
    val body = HttpReprocessEloRequest(iRating = true, elo = true)

    val response = dataController.startEloReprocessing(body)

    assertEquals(202, response.statusCode.value())
    verify { eventPublisher.publishEvent(EloReprocessingEvent) }
  }
}
