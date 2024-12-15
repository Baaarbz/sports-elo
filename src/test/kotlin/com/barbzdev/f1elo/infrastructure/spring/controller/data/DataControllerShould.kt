package com.barbzdev.f1elo.infrastructure.spring.controller.data

import com.barbzdev.f1elo.infrastructure.spring.event.EloReprocessingEvent
import com.barbzdev.f1elo.infrastructure.spring.event.IRatingReprocessingEvent
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
