package com.barbzdev.f1elo.application

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.DriverId
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.repository.DriverRepository
import java.time.LocalDate

class GetDriverByIdUseCase(
  private val driverRepository: DriverRepository,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(request: GetDriverByIdRequest): GetDriverByIdResponse = instrumentation {
    request.findDriver()?.toResponse() ?: GetDriverByIdNotFound
  }

  private fun GetDriverByIdRequest.findDriver() = driverRepository.findBy(DriverId(driverId))

  private fun Driver.toResponse() =
    GetDriverByIdSuccess(
      id = id().value,
      fullName = GetDriverByIdFullName(familyName = fullName().familyName, givenName = fullName().givenName),
      code = code()?.value,
      permanentNumber = permanentNumber()?.value,
      birthDate = birthDate().toLocalDate(),
      nationality =
        GetDriverByIdNationality(
          countryCode = nationality().countryCode,
          countryName = nationality().countryName,
          value = nationality().name,
        ),
      infoUrl = infoUrl().value,
      currentElo = GetDriverByIdElo(rating = currentElo().rating, occurredOn = currentElo().toLocalDate()),
      highestElo = highestElo().let { GetDriverByIdElo(rating = it.rating, occurredOn = it.toLocalDate()) },
      lowestElo = lowestElo().let { GetDriverByIdElo(rating = it.rating, occurredOn = it.toLocalDate()) },
      eloRecord = eloRecord().map { GetDriverByIdElo(rating = it.rating, occurredOn = it.toLocalDate()) }.sortedBy { it.occurredOn })
}

data class GetDriverByIdRequest(val driverId: String)

sealed class GetDriverByIdResponse

data object GetDriverByIdNotFound : GetDriverByIdResponse()

data class GetDriverByIdSuccess(
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

data class GetDriverByIdElo(val rating: Int, val occurredOn: LocalDate)

data class GetDriverByIdFullName(val familyName: String, val givenName: String)

data class GetDriverByIdNationality(
  val countryCode: String,
  val countryName: String,
  val value: String,
)
