package com.barbzdev.f1elo.infrastructure.spring.controller

import com.barbzdev.f1elo.application.driver.GetDriverByIdElo
import com.barbzdev.f1elo.application.driver.GetDriverByIdFullName
import com.barbzdev.f1elo.application.driver.GetDriverByIdNationality
import com.barbzdev.f1elo.application.driver.GetDriverByIdNotFound
import com.barbzdev.f1elo.application.driver.GetDriverByIdSuccess
import com.barbzdev.f1elo.application.driver.GetDriverByIdUseCase
import com.barbzdev.f1elo.application.driver.ListingDriver
import com.barbzdev.f1elo.application.driver.ListingDriverFullName
import com.barbzdev.f1elo.application.driver.ListingDriversSuccess
import com.barbzdev.f1elo.application.driver.ListingDriversUseCase
import com.barbzdev.f1elo.application.driver.NotValidDriverListingRequestResponse
import com.barbzdev.f1elo.application.driver.NotValidDriverListingSortingRequestResponse
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.DriverController
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.HttpDriversListing
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.HttpElo
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.HttpFullName
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.HttpGetDriverListingResponse
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.HttpGetDriverResponse
import com.barbzdev.f1elo.infrastructure.spring.controller.driver.HttpNationality
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class DriverControllerShould {
  private val listingDriversUseCase: ListingDriversUseCase = mockk()
  private val getDriverByIdUseCase: GetDriverByIdUseCase = mockk()

  private val controller = DriverController(listingDriversUseCase, getDriverByIdUseCase)

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

    val result = controller.getDriversListing(0, 25, "id", "asc")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.OK.value())
    assertThat(result.body)
      .isEqualTo(
        HttpGetDriverListingResponse(
          drivers =
            listOf(
              HttpDriversListing(
                id = "alonso",
                fullName = HttpFullName(familyName = "Alonso", givenName = "Fernando"),
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

    val result = controller.getDriversListing(-1, 25, "id", "asc")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
  }

  @Test
  fun `return bad request when use case returns NotValidDriverListingSortingRequestResponse`() {
    every { listingDriversUseCase.invoke(any()) } returns NotValidDriverListingSortingRequestResponse

    val result = controller.getDriversListing(-1, 25, "not-valid", "asc")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
  }

  @Test
  fun `return driver when use case success`() {
    val getDriverByIdSuccessResponse =
      GetDriverByIdSuccess(
        id = "alonso",
        fullName = GetDriverByIdFullName(familyName = "Alonso", givenName = "Fernando"),
        code = "ALO",
        permanentNumber = "14",
        birthDate = LocalDate.of(1981, 7, 29),
        nationality = GetDriverByIdNationality(countryCode = "ES", countryName = "Spain", value = "SPANISH"),
        infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        currentElo = GetDriverByIdElo(rating = 2016, occurredOn = LocalDate.of(2021, 1, 1)),
        highestElo = GetDriverByIdElo(rating = 2016, occurredOn = LocalDate.of(2021, 1, 1)),
        lowestElo = GetDriverByIdElo(rating = 2016, occurredOn = LocalDate.of(2021, 1, 1)),
        eloRecord = listOf(GetDriverByIdElo(rating = 2016, occurredOn = LocalDate.of(2021, 1, 1))))
    every { getDriverByIdUseCase.invoke(any()) } returns getDriverByIdSuccessResponse

    val result = controller.getDriver("alonso")

    val expected =
      HttpGetDriverResponse(
        id = "alonso",
        fullName = HttpFullName(familyName = "Alonso", givenName = "Fernando"),
        code = "ALO",
        permanentNumber = "14",
        birthDate = LocalDate.of(1981, 7, 29),
        nationality = HttpNationality(countryCode = "ES", countryName = "Spain", value = "SPANISH"),
        infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        currentElo = HttpElo(value = 2016, occurredOn = LocalDate.of(2021, 1, 1)),
        highestElo = HttpElo(value = 2016, occurredOn = LocalDate.of(2021, 1, 1)),
        lowestElo = HttpElo(value = 2016, occurredOn = LocalDate.of(2021, 1, 1)),
        eloRecord = listOf(HttpElo(value = 2016, occurredOn = LocalDate.of(2021, 1, 1))))
    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.OK.value())
    assertThat(result.body).isEqualTo(expected)
  }

  @Test
  fun `return not found when use case returns GetDriverByIdNotFound`() {
    every { getDriverByIdUseCase.invoke(any()) } returns GetDriverByIdNotFound

    val result = controller.getDriver("non-existing-id")

    assertThat(result.statusCode.value()).isEqualTo(HttpStatus.NOT_FOUND.value())
  }
}
