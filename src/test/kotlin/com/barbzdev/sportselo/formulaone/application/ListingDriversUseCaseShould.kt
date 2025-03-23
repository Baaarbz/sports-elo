package com.barbzdev.sportselo.formulaone.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.core.domain.util.DomainPaginated
import com.barbzdev.sportselo.core.domain.util.Page
import com.barbzdev.sportselo.core.domain.util.PageSize
import com.barbzdev.sportselo.core.domain.util.SortBy
import com.barbzdev.sportselo.core.domain.util.SortOrder
import com.barbzdev.sportselo.formulaone.application.ListingDriver
import com.barbzdev.sportselo.formulaone.application.ListingDriverFullName
import com.barbzdev.sportselo.formulaone.application.ListingDriversRequest
import com.barbzdev.sportselo.formulaone.application.ListingDriversResponse
import com.barbzdev.sportselo.formulaone.application.ListingDriversUseCase
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.factory.DriverFactory.aDriver
import com.barbzdev.sportselo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ListingDriversUseCaseShould {
  private val driverRepository: DriverRepository = mockk()
  private val instrumentation: UseCaseInstrumentation = instrumentationMock()

  private val listingDriversUseCase = ListingDriversUseCase(driverRepository, instrumentation)

  @Test
  fun `return drivers when page and size are valid`() {
    val drivers = listOf(aDriver(), aDriver())
    every { driverRepository.findAll(any(), any(), any(), any()) } returns DomainPaginated(drivers, 0, 25, 2, 1)

    val result = listingDriversUseCase.invoke(ListingDriversRequest(0, 25, "id", "desc"))

    val expected =
      ListingDriversResponse.Success(
        drivers =
          drivers.map { driver ->
            ListingDriver(
              id = driver.id().value,
              fullName =
                ListingDriverFullName(
                  familyName = driver.fullName().familyName, givenName = driver.fullName().givenName),
              currentElo = driver.currentElo().value,
              highestElo = driver.highestElo().value,
              lowestElo = driver.lowestElo().value,
              lastRaceDate = driver.currentElo().occurredOn.toLocalDate())
          },
        page = 0,
        pageSize = 25,
        totalElements = 2,
        totalPages = 1)
    assertThat(result).isEqualTo(expected)
    verify { driverRepository.findAll(Page(0), PageSize(25), SortBy("id"), SortOrder("desc")) }
  }

  @Test
  fun `return NotValidListingRequestResponse when page is not valid`() {
    val notValidPageRequest = ListingDriversRequest(-1, 25, "id", "asc")

    val result = listingDriversUseCase.invoke(notValidPageRequest)

    assertThat(result).isInstanceOf(ListingDriversResponse.BadRequest::class)
  }

  @Test
  fun `return NotValidListingRequestResponse when pageSize is not valid`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 2, "id", "asc")

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(ListingDriversResponse.BadRequest::class)
  }

  @Test
  fun `return NotValidDriverListingSortingRequestResponse when sortBy is has valid value but sortOrder not`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 25, "id", "not-valid")

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(ListingDriversResponse.BadSortingRequest::class)
  }

  @Test
  fun `return NotValidDriverListingSortingRequestResponse when sortBy does not have valid value but sortOrder has valid value`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 25, "not-valid", "desc")

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(ListingDriversResponse.BadSortingRequest::class)
  }
}
