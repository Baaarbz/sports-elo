package com.barbzdev.sportselo.core.infrastructure.framework.controller.data

import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.EloReprocessingEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/data")
class DataController(private val eventPublisher: ApplicationEventPublisher) : DataControllerDocumentation {

  @PostMapping("reprocess-elo")
  override fun startEloReprocessing(@RequestBody body: HttpReprocessEloRequest): ResponseEntity<Unit> {
    if (body.elo) eventPublisher.publishEvent(EloReprocessingEvent)

    return ResponseEntity.accepted().build()
  }
}

data class HttpReprocessEloRequest(val elo: Boolean)
