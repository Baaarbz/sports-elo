package com.barbzdev.f1elo.application

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.common.DomainPaginated
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import java.time.LocalDate

class ListingDriversUseCase(
  private val driverRepository: DriverRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(request: ListingDriversRequest): ListingDriversResponse = instrumentation {
    if (request.isNotValidRequest()) {
      return@instrumentation NotValidListingRequestResponse
    }

    request
      .findDrivers()
      .toResponse()
  }

  private fun ListingDriversRequest.isNotValidRequest() = this.page < 0 || this.pageSize !in SUPPORTED_PAGE_LIMIT

  private fun ListingDriversRequest.findDrivers() = driverRepository.findAll(this.page, this.pageSize)

  private fun DomainPaginated<Driver>.toResponse() = ListingDriversSuccess(
    drivers = this.elements.map { driver ->
      ListingDriver(
        id = driver.id().value,
        fullName = ListingDriverFullName(
          familyName = driver.fullName().familyName,
          givenName = driver.fullName().givenName
        ),
        currentElo = driver.currentElo().rating,
        highestElo = driver.highestElo().rating,
        lowestElo = driver.lowestElo().rating,
        lastRaceDate = driver.currentElo().toLocalDate()
      )
    },
    page = this.page,
    pageSize = this.pageSize,
    totalElements = this.totalElements,
    totalPages = this.totalPages
  )

  private companion object {
    val SUPPORTED_PAGE_LIMIT = intArrayOf(25, 50, 100)
  }
}

data class ListingDriversRequest(val page: Int, val pageSize: Int)

sealed class ListingDriversResponse

data object NotValidListingRequestResponse : ListingDriversResponse()

data class ListingDriversSuccess(
  val drivers: List<ListingDriver>,
  val page: Int,
  val pageSize: Int,
  val totalElements: Long,
  val totalPages: Int
) : ListingDriversResponse()

data class ListingDriver(
  val id: String,
  val fullName: ListingDriverFullName,
  val currentElo: Int,
  val highestElo: Int,
  val lowestElo: Int,
  val lastRaceDate: LocalDate
)

data class ListingDriverFullName(
  val familyName: String,
  val givenName: String
)
