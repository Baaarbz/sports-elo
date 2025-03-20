package com.barbzdev.sportselo.formulaone.infrastructure.framework.controller.data

import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.IRatingReprocessingEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/data")
class DataController(private val eventPublisher: ApplicationEventPublisher) : DataControllerDocumentation {

  @PostMapping("reprocess-ratings")
  override fun startRatingsReprocessing(@RequestBody body: HttpReprocessRatingsRequest): ResponseEntity<Unit> {
    if (body.iRating) eventPublisher.publishEvent(IRatingReprocessingEvent)
    if (body.elo) eventPublisher.publishEvent(EloReprocessingEvent)

    return ResponseEntity.accepted().build()
  }
}

data class HttpReprocessRatingsRequest(
  val iRating: Boolean,
  val elo: Boolean,
)
