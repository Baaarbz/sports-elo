package com.barbzdev.f1elo.infrastructure.spring.controller

import com.barbzdev.f1elo.application.ListingDriversRequest
import com.barbzdev.f1elo.application.ListingDriversSuccess
import com.barbzdev.f1elo.application.ListingDriversUseCase
import com.barbzdev.f1elo.application.NotValidListingRequestResponse
import java.time.LocalDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/drivers")
class DriverController(
  private val listingDriversUseCase: ListingDriversUseCase
) : DriverControllerDocumentation {

  @GetMapping
  override fun getDriversListing(    @RequestParam(defaultValue = "0") page: Int,    @RequestParam(defaultValue = "25") pageSize: Int): ResponseEntity<HttpGetDriverListingResponse> {
    val listingDriversResponse = listingDriversUseCase.invoke(ListingDriversRequest(page, pageSize))
    return when (listingDriversResponse) {
      is ListingDriversSuccess -> ResponseEntity.ok(listingDriversResponse.toHttpResponse())
      is NotValidListingRequestResponse -> ResponseEntity.badRequest().build()
    }
  }

  private fun ListingDriversSuccess.toHttpResponse() = HttpGetDriverListingResponse(
    drivers = this.drivers.map { driver ->
      HttpDriversListing(
        id = driver.id,
        fullName = HttpDriverListingFullName(
          familyName = driver.fullName.familyName,
          givenName = driver.fullName.givenName
        ),
        currentElo = driver.currentElo,
        highestElo = driver.highestElo,
        lowestElo = driver.lowestElo,
        lastRaceDate = driver.lastRaceDate
      )
    },
    page = this.page,
    pageSize = this.pageSize,
    totalElements = this.totalElements,
    totalPages = this.totalPages
  )

  @GetMapping("{driverId}")
  override fun getDriver(@PathVariable driverId: String): ResponseEntity<HttpGetDriverResponse> {
    TODO("Not yet implemented")
  }
}

data class HttpGetDriverListingResponse(
  val drivers: List<HttpDriversListing>,
  val page: Int,
  val pageSize: Int,
  val totalElements: Long,
  val totalPages: Int
)

data class HttpDriversListing(
  val id: String,
  val fullName: HttpDriverListingFullName,
  val currentElo: Int,
  val highestElo: Int,
  val lowestElo: Int,
  val lastRaceDate: LocalDate,
)

data class HttpDriverListingFullName(
  val familyName: String,
  val givenName: String
)

data class HttpGetDriverResponse(
  val id: String,
  val name: String,
  val currentElo: HttpElo,
  val highestElo: HttpElo,
  val lowestElo: HttpElo,
  val eloRecord: List<HttpElo>
)

data class HttpElo(val value: Int, val occurredOn: LocalDate)
