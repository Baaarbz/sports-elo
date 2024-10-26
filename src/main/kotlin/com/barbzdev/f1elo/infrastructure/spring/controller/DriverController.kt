package com.barbzdev.f1elo.infrastructure.spring.controller

import java.time.LocalDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/drivers")
class DriverController : DriverControllerDocumentation {

  @GetMapping("{driverId}")
  override fun getDriver(@PathVariable driverId: String): ResponseEntity<HttpGetDriverResponse> {
    TODO("Not yet implemented")
  }
}

data class HttpGetDriverResponse(
  val id: String,
  val name: String,
  val currentElo: HttpElo,
  val highestElo: HttpElo,
  val lowestElo: HttpElo,
  val eloRecord: List<HttpElo>
)

data class HttpElo(val value: Int, val occurredOn: LocalDate)
