package com.barbzdev.sportselo.infrastructure.spring.controller.driver

import com.barbzdev.sportselo.application.driver.GetDriverByIdNotFound
import com.barbzdev.sportselo.application.driver.GetDriverByIdRequest
import com.barbzdev.sportselo.application.driver.GetDriverByIdSuccess
import com.barbzdev.sportselo.application.driver.GetDriverByIdUseCase
import com.barbzdev.sportselo.application.driver.ListingDriversRequest
import com.barbzdev.sportselo.application.driver.ListingDriversSuccess
import com.barbzdev.sportselo.application.driver.ListingDriversUseCase
import com.barbzdev.sportselo.application.driver.NotValidDriverListingRequestResponse
import com.barbzdev.sportselo.application.driver.NotValidDriverListingSortingRequestResponse
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
  private val listingDriversUseCase: ListingDriversUseCase,
  private val getDriverByIdUseCase: GetDriverByIdUseCase
) : DriverControllerDocumentation {

  @GetMapping
  override fun getDriversListing(
    @RequestParam(defaultValue = "0") page: Int,
    @RequestParam(defaultValue = "25") pageSize: Int,
    @RequestParam(defaultValue = "id") sortBy: String,
    @RequestParam(defaultValue = "desc") sortOrder: String
  ): ResponseEntity<HttpGetDriverListingResponse> {
    val listingDriversResponse = listingDriversUseCase.invoke(ListingDriversRequest(page, pageSize, sortBy, sortOrder))
    return when (listingDriversResponse) {
      is ListingDriversSuccess -> ResponseEntity.ok(listingDriversResponse.toHttpResponse())
      is NotValidDriverListingRequestResponse,
      NotValidDriverListingSortingRequestResponse -> ResponseEntity.badRequest().build()
    }
  }

  @GetMapping("{driverId}")
  override fun getDriver(@PathVariable driverId: String): ResponseEntity<HttpGetDriverResponse> {
    val driverResponse = getDriverByIdUseCase.invoke(GetDriverByIdRequest(driverId))
    return when (driverResponse) {
      is GetDriverByIdSuccess -> ResponseEntity.ok(driverResponse.toHttpResponse())
      is GetDriverByIdNotFound -> ResponseEntity.notFound().build()
    }
  }

  private fun ListingDriversSuccess.toHttpResponse() =
    HttpGetDriverListingResponse(
      drivers =
        this.drivers.map { driver ->
          HttpDriversListing(
            id = driver.id,
            fullName = HttpFullName(familyName = driver.fullName.familyName, givenName = driver.fullName.givenName),
            currentElo = driver.currentElo,
            highestElo = driver.highestElo,
            lowestElo = driver.lowestElo,
            lastRaceDate = driver.lastRaceDate,
            currentIRating = driver.currentIRating,
            highestIRating = driver.highestIRating,
            lowestIRating = driver.lowestIRating,
          )
        },
      page = this.page,
      pageSize = this.pageSize,
      totalElements = this.totalElements,
      totalPages = this.totalPages)

  private fun GetDriverByIdSuccess.toHttpResponse() =
    HttpGetDriverResponse(
      id = id,
      fullName = HttpFullName(familyName = fullName.familyName, givenName = fullName.givenName),
      code = code,
      permanentNumber = permanentNumber,
      birthDate = birthDate,
      nationality =
        HttpNationality(
          countryCode = nationality.countryCode, countryName = nationality.countryName, value = nationality.value),
      infoUrl = infoUrl,
      currentElo = HttpElo(value = currentElo.rating, occurredOn = currentElo.occurredOn),
      highestElo = HttpElo(value = highestElo.rating, occurredOn = highestElo.occurredOn),
      lowestElo = HttpElo(value = lowestElo.rating, occurredOn = lowestElo.occurredOn),
      eloRecord = eloRecord.map { HttpElo(value = it.rating, occurredOn = it.occurredOn) },
      currentIRating = HttpIRating(value = currentIRating.rating, occurredOn = currentIRating.occurredOn),
      highestIRating = HttpIRating(value = highestIRating.rating, occurredOn = highestIRating.occurredOn),
      lowestIRating = HttpIRating(value = lowestIRating.rating, occurredOn = lowestIRating.occurredOn),
      iRatingRecord = iRatingRecord.map { HttpIRating(value = it.rating, occurredOn = it.occurredOn) },
    )
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
  val fullName: HttpFullName,
  val currentElo: Int,
  val highestElo: Int,
  val lowestElo: Int,
  val currentIRating: Int,
  val highestIRating: Int,
  val lowestIRating: Int,
  val lastRaceDate: LocalDate,
)

data class HttpGetDriverResponse(
  val id: String,
  val fullName: HttpFullName,
  val code: String?,
  val permanentNumber: String?,
  val birthDate: LocalDate,
  val nationality: HttpNationality,
  val infoUrl: String,
  val currentElo: HttpElo,
  val highestElo: HttpElo,
  val lowestElo: HttpElo,
  val eloRecord: List<HttpElo>,
  val currentIRating: HttpIRating,
  val highestIRating: HttpIRating,
  val lowestIRating: HttpIRating,
  val iRatingRecord: List<HttpIRating>
)

data class HttpFullName(val familyName: String, val givenName: String)

data class HttpElo(val value: Int, val occurredOn: LocalDate)

data class HttpIRating(val value: Int, val occurredOn: LocalDate)

data class HttpNationality(val countryCode: String, val countryName: String, val value: String)
