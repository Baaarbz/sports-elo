package com.barbzdev.sportselo.formulaone.infrastructure.framework.controller.driver

import com.barbzdev.sportselo.formulaone.application.GetDriverByIdRequest
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdResponse
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdUseCase
import com.barbzdev.sportselo.formulaone.application.ListingDriversRequest
import com.barbzdev.sportselo.formulaone.application.ListingDriversResponse
import com.barbzdev.sportselo.formulaone.application.ListingDriversUseCase
import java.time.LocalDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/formula-one/drivers")
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
      is ListingDriversResponse.Success -> ResponseEntity.ok(listingDriversResponse.toHttpResponse())
      is ListingDriversResponse.BadSortingRequest -> ResponseEntity.badRequest().build()
      is ListingDriversResponse.BadRequest -> ResponseEntity.badRequest().build()
    }
  }

  @GetMapping("{driverId}")
  override fun getDriver(@PathVariable driverId: String): ResponseEntity<HttpGetDriverResponse> {
    val driverResponse = getDriverByIdUseCase.invoke(GetDriverByIdRequest(driverId))
    return when (driverResponse) {
      is GetDriverByIdResponse.Success -> ResponseEntity.ok(driverResponse.toHttpResponse())
      is GetDriverByIdResponse.NotFound -> ResponseEntity.notFound().build()
    }
  }

  private fun ListingDriversResponse.Success.toHttpResponse() =
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
          )
        },
      page = this.page,
      pageSize = this.pageSize,
      totalElements = this.totalElements,
      totalPages = this.totalPages)

  private fun GetDriverByIdResponse.Success.toHttpResponse() =
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
      eloRecord = eloRecord.map { HttpElo(value = it.rating, occurredOn = it.occurredOn) })
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
)

data class HttpFullName(val familyName: String, val givenName: String)

data class HttpElo(val value: Int, val occurredOn: LocalDate)

data class HttpIRating(val value: Int, val occurredOn: LocalDate)

data class HttpNationality(val countryCode: String, val countryName: String, val value: String)
