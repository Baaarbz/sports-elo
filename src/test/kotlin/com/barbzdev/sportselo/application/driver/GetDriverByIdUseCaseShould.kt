package com.barbzdev.sportselo.application.driver

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdElo
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdFullName
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdNationality
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdRequest
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdResponse
import com.barbzdev.sportselo.formulaone.application.GetDriverByIdUseCase
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.factory.DriverFactory.aDriver
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
      GetDriverByIdResponse.Success(
        id = aDriver.id().value,
        fullName =
          GetDriverByIdFullName(familyName = aDriver.fullName().familyName, givenName = aDriver.fullName().givenName),
        code = aDriver.code()?.value,
        permanentNumber = aDriver.permanentNumber()?.value,
        birthDate = aDriver.birthDate().date.toLocalDate(),
        nationality =
          GetDriverByIdNationality(
            countryCode = aDriver.nationality().countryCode,
            countryName = aDriver.nationality().countryName,
            value = aDriver.nationality().name,
          ),
        infoUrl = aDriver.infoUrl().value,
        currentElo =
          GetDriverByIdElo(
            rating = aDriver.currentElo().value, occurredOn = aDriver.currentElo().occurredOn.toLocalDate()),
        highestElo =
          GetDriverByIdElo(
            rating = aDriver.highestElo().value, occurredOn = aDriver.highestElo().occurredOn.toLocalDate()),
        lowestElo =
          GetDriverByIdElo(
            rating = aDriver.lowestElo().value, occurredOn = aDriver.lowestElo().occurredOn.toLocalDate()),
        eloRecord =
          aDriver.eloRecord().map { GetDriverByIdElo(rating = it.value, occurredOn = it.occurredOn.toLocalDate()) },
      )
    assertThat(response).isInstanceOf(GetDriverByIdResponse.Success::class)
    assertThat(response).isEqualTo(expected)
  }

  @Test
  fun `return not found when driver does not exist`() {
    val request = GetDriverByIdRequest("non-existing-id")
    every { driverRepository.findBy(any()) } returns null

    val response = service(request)

    assertThat(response).isInstanceOf(GetDriverByIdResponse.NotFound::class)
  }
}
