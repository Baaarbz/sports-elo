package com.barbzdev.sportselo.application.driver

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.domain.repository.DriverRepository
import com.barbzdev.sportselo.factory.DriverFactory.aDriver
import com.barbzdev.sportselo.observability.instrumentationMock
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GetDriverByIdUseCaseShould {
  private val driverRepository: DriverRepository = mockk()
  private val instrumentation: UseCaseInstrumentation = instrumentationMock()

  private val service = GetDriverByIdUseCase(driverRepository, instrumentation)

  @Test
  fun `return driver by id`() {
    val request = GetDriverByIdRequest("driver-id")
    val aDriver = aDriver()
    every { driverRepository.findBy(any()) } returns aDriver

    val response = service(request)

    val expected =
      GetDriverByIdSuccess(
        id = aDriver.id().value,
        fullName =
          GetDriverByIdFullName(familyName = aDriver.fullName().familyName, givenName = aDriver.fullName().givenName),
        code = aDriver.code()?.value,
        permanentNumber = aDriver.permanentNumber()?.value,
        birthDate = aDriver.birthDate().toLocalDate(),
        nationality =
          GetDriverByIdNationality(
            countryCode = aDriver.nationality().countryCode,
            countryName = aDriver.nationality().countryName,
            value = aDriver.nationality().name,
          ),
        infoUrl = aDriver.infoUrl().value,
        currentElo =
          GetDriverByIdElo(rating = aDriver.currentElo().value, occurredOn = aDriver.currentElo().toLocalDate()),
        highestElo =
          GetDriverByIdElo(rating = aDriver.highestElo().value, occurredOn = aDriver.highestElo().toLocalDate()),
        lowestElo =
          GetDriverByIdElo(rating = aDriver.lowestElo().value, occurredOn = aDriver.lowestElo().toLocalDate()),
        eloRecord = aDriver.eloRecord().map { GetDriverByIdElo(rating = it.value, occurredOn = it.toLocalDate()) },
        currentIRating =
          GetDriverByIdIRating(
            rating = aDriver.currentIRating().value, occurredOn = aDriver.currentIRating().toLocalDate()),
        highestIRating =
          GetDriverByIdIRating(
            rating = aDriver.highestIRating().value, occurredOn = aDriver.highestIRating().toLocalDate()),
        lowestIRating =
          GetDriverByIdIRating(
            rating = aDriver.lowestIRating().value, occurredOn = aDriver.lowestIRating().toLocalDate()),
        iRatingRecord =
          aDriver.iRatingRecord().map { GetDriverByIdIRating(rating = it.value, occurredOn = it.toLocalDate()) },
      )
    assertThat(response).isInstanceOf(GetDriverByIdSuccess::class)
    assertThat(response).isEqualTo(expected)
  }

  @Test
  fun `return not found when driver does not exist`() {
    val request = GetDriverByIdRequest("non-existing-id")
    every { driverRepository.findBy(any()) } returns null

    val response = service(request)

    assertThat(response).isInstanceOf(GetDriverByIdNotFound::class)
  }
}
