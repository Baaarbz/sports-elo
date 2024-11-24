package com.barbzdev.f1elo.infrastructure.spring.controller

import com.barbzdev.f1elo.application.ListingDriver
import com.barbzdev.f1elo.application.ListingDriverFullName
import com.barbzdev.f1elo.application.ListingDriversSuccess
import com.barbzdev.f1elo.application.ListingDriversUseCase
import com.barbzdev.f1elo.application.NotValidDriverListingRequestResponse
import com.barbzdev.f1elo.application.NotValidDriverListingSortingRequestResponse
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class DriverControllerShould {
  private val listingDriversUseCase: ListingDriversUseCase = mockk()

  private val driverController = DriverController(listingDriversUseCase)

  @Test
  fun `return drivers when use case success`() {
    val responseUseCase =
      ListingDriversSuccess(
        drivers =
          listOf(
            ListingDriver(
              id = "alonso",
              fullName = ListingDriverFullName(familyName = "Alonso", givenName = "Fernando"),
              currentElo = 2016,
              highestElo = 2016,
              lowestElo = 2016,
              lastRaceDate = LocalDate.of(2021, 1, 1)),
          ),
        page = 0,
        pageSize = 25,
        totalElements = 2,
        totalPages = 1)
    every { listingDriversUseCase.invoke(any()) } returns responseUseCase

    val result = driverController.getDriversListing(0, 25, "id", "asc")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.OK.value())
    assertThat(result.body)
      .isEqualTo(
        HttpGetDriverListingResponse(
          drivers =
            listOf(
              HttpDriversListing(
                id = "alonso",
                fullName = HttpDriverListingFullName(familyName = "Alonso", givenName = "Fernando"),
                currentElo = 2016,
                highestElo = 2016,
                lowestElo = 2016,
                lastRaceDate = LocalDate.of(2021, 1, 1))),
          page = 0,
          pageSize = 25,
          totalElements = 2,
          totalPages = 1))
  }

  @Test
  fun `return bad request when use case returns NotValidDriverListingRequestResponse`() {
    every { listingDriversUseCase.invoke(any()) } returns NotValidDriverListingRequestResponse

    val result = driverController.getDriversListing(-1, 25, "id", "asc")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
  }

  @Test
  fun `return bad request when use case returns NotValidDriverListingSortingRequestResponse`() {
    every { listingDriversUseCase.invoke(any()) } returns NotValidDriverListingSortingRequestResponse

    val result = driverController.getDriversListing(-1, 25, "not-valid", "asc")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
  }
}
