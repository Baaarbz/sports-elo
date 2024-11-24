package com.barbzdev.f1elo.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.factory.DriverFactory.aDriver
import com.barbzdev.f1elo.observability.instrumentationMock
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
          GetDriverByIdElo(rating = aDriver.currentElo().rating, occurredOn = aDriver.currentElo().toLocalDate()),
        highestElo =
          GetDriverByIdElo(rating = aDriver.highestElo().rating, occurredOn = aDriver.highestElo().toLocalDate()),
        lowestElo =
          GetDriverByIdElo(rating = aDriver.lowestElo().rating, occurredOn = aDriver.lowestElo().toLocalDate()),
        eloRecord = aDriver.eloRecord().map { GetDriverByIdElo(rating = it.rating, occurredOn = it.toLocalDate()) })
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
