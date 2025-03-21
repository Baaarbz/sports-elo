package com.barbzdev.sportselo.formulaone.application

import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import java.time.LocalDate

class GetDriverByIdUseCase(
  private val driverRepository: DriverRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(request: GetDriverByIdRequest): GetDriverByIdResponse = instrumentation {
    request.findDriver()?.toResponse() ?: GetDriverByIdResponse.NotFound
  }

  private fun GetDriverByIdRequest.findDriver() = driverRepository.findBy(SportsmanId(driverId))

  private fun Driver.toResponse() =
    GetDriverByIdResponse.Success(
      id = id().value,
      fullName = GetDriverByIdFullName(familyName = fullName().familyName, givenName = fullName().givenName),
      code = code()?.value,
      permanentNumber = permanentNumber()?.value,
      birthDate = birthDate().date.toLocalDate(),
      nationality =
      GetDriverByIdNationality(
        countryCode = nationality().countryCode,
        countryName = nationality().countryName,
        value = nationality().name,
      ),
      infoUrl = infoUrl().value,
      currentElo = GetDriverByIdElo(rating = currentElo().value, occurredOn = currentElo().occurredOn.toLocalDate()),
      highestElo = highestElo().let { GetDriverByIdElo(rating = it.value, occurredOn = it.occurredOn.toLocalDate()) },
      lowestElo = lowestElo().let { GetDriverByIdElo(rating = it.value, occurredOn = it.occurredOn.toLocalDate()) },
      eloRecord = eloRecord().map { GetDriverByIdElo(rating = it.value, occurredOn = it.occurredOn.toLocalDate()) }
    )
}

data class GetDriverByIdRequest(val driverId: String)

sealed class GetDriverByIdResponse {
  data object NotFound : GetDriverByIdResponse()
  data class Success(
    val id: String,
    val fullName: GetDriverByIdFullName,
    val code: String?,
    val permanentNumber: String?,
    val birthDate: LocalDate,
    val nationality: GetDriverByIdNationality,
    val infoUrl: String,
    val currentElo: GetDriverByIdElo,
    val highestElo: GetDriverByIdElo,
    val lowestElo: GetDriverByIdElo,
    val eloRecord: List<GetDriverByIdElo>,
  ) : GetDriverByIdResponse()
}


data class GetDriverByIdElo(val rating: Int, val occurredOn: LocalDate)

data class GetDriverByIdIRating(val rating: Int, val occurredOn: LocalDate)

data class GetDriverByIdFullName(val familyName: String, val givenName: String)

data class GetDriverByIdNationality(
  val countryCode: String,
  val countryName: String,
  val value: String,
)
