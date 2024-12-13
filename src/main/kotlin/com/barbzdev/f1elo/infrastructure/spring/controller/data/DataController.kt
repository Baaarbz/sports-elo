package com.barbzdev.f1elo.infrastructure.spring.controller.data

import com.barbzdev.f1elo.infrastructure.spring.event.EloReprocessingEvent
import com.barbzdev.f1elo.infrastructure.spring.event.IRatingReprocessingEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/data")
class TheoreticalPerformanceController(private val eventPublisher: ApplicationEventPublisher) :
  DataControllerDocumentation {

  @PostMapping("reprocess-ratings")
  override fun startRatingsReprocessing(body: HttpReprocessRatingsRequest): ResponseEntity<Unit> {
    if (body.iRating) eventPublisher.publishEvent(IRatingReprocessingEvent)
    if (body.elo) eventPublisher.publishEvent(EloReprocessingEvent)

    return ResponseEntity.accepted().build()
  }
}

data class HttpReprocessRatingsRequest(
  val iRating: Boolean,
  val elo: Boolean,
)
