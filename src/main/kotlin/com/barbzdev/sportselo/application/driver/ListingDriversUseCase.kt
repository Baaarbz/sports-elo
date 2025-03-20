package com.barbzdev.sportselo.application.driver

import com.barbzdev.sportselo.domain.Driver
import com.barbzdev.sportselo.domain.common.DomainPaginated
import com.barbzdev.sportselo.domain.common.Page
import com.barbzdev.sportselo.domain.common.PageSize
import com.barbzdev.sportselo.domain.common.SortBy
import com.barbzdev.sportselo.domain.common.SortOrder
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.DriverRepository
import java.time.LocalDate

class ListingDriversUseCase(
  private val driverRepository: DriverRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(request: ListingDriversRequest): ListingDriversResponse = instrumentation {
    if (request.isNotValidRequest()) {
      return@instrumentation NotValidDriverListingRequestResponse
    }
    if (request.isNotValidSorting()) {
      return@instrumentation NotValidDriverListingSortingRequestResponse
    }

    request.findDrivers().toResponse()
  }

  private fun ListingDriversRequest.isNotValidRequest() = page < 0 || pageSize !in SUPPORTED_PAGE_LIMIT

  private fun ListingDriversRequest.isNotValidSorting() =
    sortBy !in SUPPORTED_SORTING_BY || sortOrder !in SUPPORTED_SORTING_ORDER

  private fun ListingDriversRequest.findDrivers() =
    driverRepository.findAll(Page(page), PageSize(pageSize), SortBy(sortBy), SortOrder(sortOrder))

  private fun DomainPaginated<Driver>.toResponse() =
    ListingDriversSuccess(
      drivers =
        this.elements.map { driver ->
          ListingDriver(
            id = driver.id().value,
            fullName =
              ListingDriverFullName(familyName = driver.fullName().familyName, givenName = driver.fullName().givenName),
            currentElo = driver.currentElo().value,
            highestElo = driver.highestElo().value,
            lowestElo = driver.lowestElo().value,
            lastRaceDate = driver.currentElo().toLocalDate(),
            currentIRating = driver.currentIRating().value,
            highestIRating = driver.highestIRating().value,
            lowestIRating = driver.lowestIRating().value,
          )
        },
      page = this.page,
      pageSize = this.pageSize,
      totalElements = this.totalElements,
      totalPages = this.totalPages)

  private companion object {
    val SUPPORTED_PAGE_LIMIT = intArrayOf(10, 25, 50, 100)
    val SUPPORTED_SORTING_BY =
      arrayOf("currentElo", "highestElo", "lowestElo", "currentIRating", "highestIRating", "lowestIRating", "id")
    val SUPPORTED_SORTING_ORDER = arrayOf("asc", "desc")
  }
}

data class ListingDriversRequest(val page: Int, val pageSize: Int, val sortBy: String, val sortOrder: String)

sealed class ListingDriversResponse

data object NotValidDriverListingRequestResponse : ListingDriversResponse()

data object NotValidDriverListingSortingRequestResponse : ListingDriversResponse()

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
  val currentIRating: Int,
  val highestIRating: Int,
  val lowestIRating: Int,
  val lastRaceDate: LocalDate
)

data class ListingDriverFullName(val familyName: String, val givenName: String)
