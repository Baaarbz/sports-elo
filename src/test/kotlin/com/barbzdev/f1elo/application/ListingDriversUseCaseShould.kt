package com.barbzdev.f1elo.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.common.DomainPaginated
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
    every { driverRepository.findAll(any(), any()) } returns DomainPaginated(drivers, 0, 25, 2, 1)

    val result = listingDriversUseCase.invoke(ListingDriversRequest(0, 25))

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
    verify { driverRepository.findAll(0, 25) }
  }

  @Test
  fun `return NotValidListingRequestResponse when page is not valid`() {
    val notValidPageRequest = ListingDriversRequest(-1, 25)

    val result = listingDriversUseCase.invoke(notValidPageRequest)

    assertThat(result).isInstanceOf(NotValidListingRequestResponse::class)
  }

  @Test
  fun `return NotValidListingRequestResponse when pageSize is not valid`() {
    val notValidPageSizeRequest = ListingDriversRequest(0, 2)

    val result = listingDriversUseCase.invoke(notValidPageSizeRequest)

    assertThat(result).isInstanceOf(NotValidListingRequestResponse::class)
  }
}
