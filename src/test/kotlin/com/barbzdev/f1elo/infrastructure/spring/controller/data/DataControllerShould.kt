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
  fun `start elo reprocessing`() {
    val response = dataController.startRatingsReprocessing("elo")

    assertEquals(202, response.statusCode.value())
    verify { eventPublisher.publishEvent(EloReprocessingEvent) }
  }

  @Test
  fun `start iRating reprocessing`() {
    val response = dataController.startRatingsReprocessing("irating")

    assertEquals(202, response.statusCode.value())
    verify { eventPublisher.publishEvent(IRatingReprocessingEvent) }
  }
}
