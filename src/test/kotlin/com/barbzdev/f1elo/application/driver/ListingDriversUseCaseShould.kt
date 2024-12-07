package com.barbzdev.f1elo.application.driver

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.application.driver.ListingDriver
import com.barbzdev.f1elo.application.driver.ListingDriverFullName
import com.barbzdev.f1elo.application.driver.ListingDriversRequest
import com.barbzdev.f1elo.application.driver.ListingDriversSuccess
import com.barbzdev.f1elo.application.driver.ListingDriversUseCase
import com.barbzdev.f1elo.application.driver.NotValidDriverListingRequestResponse
import com.barbzdev.f1elo.application.driver.NotValidDriverListingSortingRequestResponse
import com.barbzdev.f1elo.domain.common.DomainPaginated
import com.barbzdev.f1elo.domain.common.Page
import com.barbzdev.f1elo.domain.common.PageSize
import com.barbzdev.f1elo.domain.common.SortBy
import com.barbzdev.f1elo.domain.common.SortOrder
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.factory.DriverFactory.aDriver
import com.barbzdev.f1elo.observability.instrumentationMock
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
      ListingDriversSuccess(
        drivers =
          drivers.map { driver ->
            ListingDriver(
              id = driver.id().value,
              fullName =
                ListingDriverFullName(
                  familyName = driver.fullName().familyName, givenName = driver.fullName().givenName),
              currentElo = driver.currentElo().rating,
              highestElo = driver.highestElo().rating,
              lowestElo = driver.lowestElo().rating,
              lastRaceDate = driver.currentElo().toLocalDate())
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

    assertThat(result).isInstanceOf(NotValidDriverListingRequestResponse::class)
  }

  @Test
  fun `return NotValidListingRequestResponse when pageSize is not valid`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 2, "id", "asc")

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(NotValidDriverListingRequestResponse::class)
  }

  @Test
  fun `return NotValidDriverListingSortingRequestResponse when sortBy is has valid value but sortOrder not`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 25, "id", "not-valid")

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(NotValidDriverListingSortingRequestResponse::class)
  }

  @Test
  fun `return NotValidDriverListingSortingRequestResponse when sortBy does not have valid value but sortOrder has valid value`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 25, "not-valid", "desc")

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(NotValidDriverListingSortingRequestResponse::class)
  }
}
