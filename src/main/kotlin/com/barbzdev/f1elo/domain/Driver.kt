package com.barbzdev.f1elo.domain

import com.barbzdev.f1elo.domain.common.Elo
import com.barbzdev.f1elo.domain.common.InfoUrl
import com.barbzdev.f1elo.domain.common.Nationality
import com.barbzdev.f1elo.domain.common.OccurredOn


class Driver private constructor(
  private val id: DriverId,
  private val fullName: DriverFullName,
  private val code: DriverCode?,
  private val permanentNumber: PermanentNumber?,
  private val birthDate: BirthDate,
  private val nationality: Nationality,
  private val infoUrl: InfoUrl,
  private val currentElo: Elo,
  private val eloRecord: Set<Elo>
) {

  fun id() = id

  fun fullName() = fullName

  fun birthDate() = birthDate

  fun nationality() = nationality

  fun infoUrl() = infoUrl

  fun currentElo() = currentElo

  fun eloRecord() = eloRecord

  fun highestElo(): Elo? = eloRecord.maxByOrNull { it.rating }

  fun lowestElo(): Elo? = eloRecord.minByOrNull { it.rating }

  fun updateElo(value: Int, occurredOn: String): Driver =
    Driver(
      id,
      fullName,
      code,
      permanentNumber,
      birthDate,
      nationality,
      infoUrl,
      Elo(value, occurredOn),
      eloRecord.plus(Elo(value, occurredOn))
    )

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Driver

    if (id != other.id) return false
    if (fullName != other.fullName) return false
    if (code != other.code) return false
    if (permanentNumber != other.permanentNumber) return false
    if (birthDate != other.birthDate) return false
    if (nationality != other.nationality) return false
    if (infoUrl != other.infoUrl) return false
    if (currentElo != other.currentElo) return false
    if (eloRecord != other.eloRecord) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + fullName.hashCode()
    result = 31 * result + (code?.hashCode() ?: 0)
    result = 31 * result + (permanentNumber?.hashCode() ?: 0)
    result = 31 * result + birthDate.hashCode()
    result = 31 * result + nationality.hashCode()
    result = 31 * result + infoUrl.hashCode()
    result = 31 * result + currentElo.hashCode()
    result = 31 * result + eloRecord.hashCode()
    return result
  }

  override fun toString(): String =
    "Driver(id=$id, fullName=$fullName, code=$code, permanentNumber=$permanentNumber, birthDate=$birthDate, nationality=$nationality, infoUrl=$infoUrl, currentElo=$currentElo, eloRecord=$eloRecord)"

  companion object {
    fun createRookie(
      id: String,
      givenName: String,
      familyName: String,
      code: String?,
      permanentNumber: String?,
      birthDate: String,
      nationality: String,
      infoUrl: String,
      debutDate: String
    ) = Driver(
      id = DriverId(id),
      fullName = DriverFullName(givenName, familyName),
      code = code?.let { DriverCode(it) },
      permanentNumber = permanentNumber?.let { PermanentNumber(it) },
      birthDate = BirthDate(birthDate),
      nationality = Nationality.fromGentilic(nationality),
      infoUrl = InfoUrl(infoUrl),
      currentElo = Elo(BASE_ELO, debutDate),
      eloRecord = setOf(Elo(BASE_ELO, debutDate))
    )

    fun create(
      id: String,
      givenName: String,
      familyName: String,
      code: String?,
      permanentNumber: String?,
      birthDate: String,
      nationality: String,
      infoUrl: String,
      currentElo: Int,
      currentEloOccurredOn: String,
      eloRecord: Map<Int, String>,
    ) = Driver(
      id = DriverId(id),
      fullName = DriverFullName(givenName, familyName),
      code = code?.let { DriverCode(it) },
      permanentNumber = permanentNumber?.let { PermanentNumber(it) },
      birthDate = BirthDate(birthDate),
      nationality = Nationality.fromGentilic(nationality),
      infoUrl = InfoUrl(infoUrl),
      currentElo = Elo(currentElo, currentEloOccurredOn),
      eloRecord = eloRecord.map { (elo, date) -> Elo(elo, date) }.toSet(),
    )

    private const val BASE_ELO = 1000
  }
}

data class DriverId(val value: String) {
  init {
    require(value.isNotBlank()) { "DriverId cannot be blank" }
  }
}

data class DriverFullName(val givenName: String, val familyName: String) {
  init {
    require(givenName.isNotBlank()) { "Given name cannot be blank" }
    require(familyName.isNotBlank()) { "Family name cannot be blank" }
  }
}

data class BirthDate(val date: String) : OccurredOn(date)

data class DriverCode(val value: String) {
  init {
    require(value.isNotBlank()) { "DriverCode cannot be blank" }
  }
}

data class PermanentNumber(val value: String) {
  init {
    require(value.isNotBlank()) { "Permanent number cannot be blank" }
    require(value.toIntOrNull() != null) { "Permanent number must be a number" }
    require(value.toInt() > 0) { "Permanent number must be greater than 0" }
  }
}
