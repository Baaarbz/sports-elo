package com.barbzdev.sportselo.formulaone.application.driver

import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.domain.DriverId
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
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
      currentElo = GetDriverByIdElo(rating = currentElo().value, occurredOn = currentElo().toLocalDate()),
      highestElo = highestElo().let { GetDriverByIdElo(rating = it.value, occurredOn = it.toLocalDate()) },
      lowestElo = lowestElo().let { GetDriverByIdElo(rating = it.value, occurredOn = it.toLocalDate()) },
      eloRecord = eloRecord().map { GetDriverByIdElo(rating = it.value, occurredOn = it.toLocalDate()) },
      currentIRating =
        GetDriverByIdIRating(rating = currentIRating().value, occurredOn = currentIRating().toLocalDate()),
      highestIRating = highestIRating().let { GetDriverByIdIRating(rating = it.value, occurredOn = it.toLocalDate()) },
      lowestIRating = lowestIRating().let { GetDriverByIdIRating(rating = it.value, occurredOn = it.toLocalDate()) },
      iRatingRecord = iRatingRecord().map { GetDriverByIdIRating(rating = it.value, occurredOn = it.toLocalDate()) },
    )
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
  val currentIRating: GetDriverByIdIRating,
  val highestIRating: GetDriverByIdIRating,
  val lowestIRating: GetDriverByIdIRating,
  val iRatingRecord: List<GetDriverByIdIRating>,
) : GetDriverByIdResponse()

data class GetDriverByIdElo(val rating: Int, val occurredOn: LocalDate)

data class GetDriverByIdIRating(val rating: Int, val occurredOn: LocalDate)

data class GetDriverByIdFullName(val familyName: String, val givenName: String)

data class GetDriverByIdNationality(
  val countryCode: String,
  val countryName: String,
  val value: String,
)
