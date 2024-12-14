package com.barbzdev.f1elo.domain

import com.barbzdev.f1elo.domain.common.Elo
import com.barbzdev.f1elo.domain.common.IRating
import com.barbzdev.f1elo.domain.common.InfoUrl
import com.barbzdev.f1elo.domain.common.Nationality
import com.barbzdev.f1elo.domain.common.OccurredOn

class Driver
private constructor(
  private val id: DriverId,
  private val fullName: DriverFullName,
  private val code: DriverCode?,
  private val permanentNumber: PermanentNumber?,
  private val birthDate: BirthDate,
  private val nationality: Nationality,
  private val infoUrl: InfoUrl,
  private val currentElo: Elo,
  private val eloRecord: List<Elo>,
  private val currentIRating: IRating,
  private val iRatingRecord: List<IRating>
) {

  fun id() = id

  fun fullName() = fullName

  fun code() = code

  fun permanentNumber() = permanentNumber

  fun birthDate() = birthDate

  fun nationality() = nationality

  fun infoUrl() = infoUrl

  fun currentElo() = currentElo

  fun eloRecord() = eloRecord.sortedBy { it.occurredOn }

  fun currentIRating() = currentIRating

  fun iRatingRecord() = iRatingRecord.sortedBy { it.occurredOn }

  fun highestElo(): Elo = eloRecord.maxByOrNull { it.value }!!

  fun highestIRating(): IRating = iRatingRecord.maxByOrNull { it.value }!!

  fun lowestElo(): Elo = eloRecord.minByOrNull { it.value }!!

  fun lowestIRating(): IRating = iRatingRecord.minByOrNull { it.value }!!

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
      eloRecord.plus(Elo(value, occurredOn)),
      currentIRating,
      iRatingRecord,
    )

  fun updateIRating(value: Int, occurredOn: String): Driver =
    Driver(
      id,
      fullName,
      code,
      permanentNumber,
      birthDate,
      nationality,
      infoUrl,
      currentElo,
      eloRecord,
      IRating(value, occurredOn),
      iRatingRecord.plus(IRating(value, occurredOn)),
    )

  fun resetElo(): Driver {
    return Driver(
      id,
      fullName,
      code,
      permanentNumber,
      birthDate,
      nationality,
      infoUrl,
      eloRecord().first(),
      listOf(eloRecord().first()),
      currentIRating,
      iRatingRecord,
    )
  }

  fun resetIRating(): Driver {
    return Driver(
      id,
      fullName,
      code,
      permanentNumber,
      birthDate,
      nationality,
      infoUrl,
      currentElo,
      eloRecord,
      iRatingRecord().first(),
      listOf(iRatingRecord().first()),
    )
  }

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
    if (currentIRating != other.currentIRating) return false
    if (iRatingRecord != other.iRatingRecord) return false

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
    result = 31 * result + currentIRating.hashCode()
    result = 31 * result + iRatingRecord.hashCode()
    return result
  }

  override fun toString(): String {
    return "Driver(id=$id, fullName=$fullName, code=$code, permanentNumber=$permanentNumber, birthDate=$birthDate, nationality=$nationality, infoUrl=$infoUrl, currentElo=$currentElo, eloRecord=$eloRecord, currentIRating=$currentIRating, iRatingRecord=$iRatingRecord)"
  }

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
    ) =
      Driver(
        id = DriverId(id),
        fullName = DriverFullName(givenName, familyName),
        code = code?.let { DriverCode(it) },
        permanentNumber = permanentNumber?.let { PermanentNumber(it) },
        birthDate = BirthDate(birthDate),
        nationality = Nationality.fromGentilic(nationality),
        infoUrl = InfoUrl(infoUrl),
        currentElo = Elo(BASE_ELO, debutDate),
        eloRecord = listOf(Elo(BASE_ELO, debutDate)),
        currentIRating = IRating(BASE_IRATING, debutDate),
        iRatingRecord = listOf(IRating(BASE_IRATING, debutDate)),
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
      eloRecord: Map<String, Int>,
      currentIRating: Int,
      currentIRatingOccurredOn: String,
      iRatingRecord: Map<String, Int>,
    ) =
      Driver(
        id = DriverId(id),
        fullName = DriverFullName(givenName, familyName),
        code = code?.let { DriverCode(it) },
        permanentNumber = permanentNumber?.let { PermanentNumber(it) },
        birthDate = BirthDate(birthDate),
        nationality = Nationality.fromGentilic(nationality),
        infoUrl = InfoUrl(infoUrl),
        currentElo = Elo(currentElo, currentEloOccurredOn),
        eloRecord = eloRecord.map { (date, elo) -> Elo(elo, date) }.toList(),
        currentIRating = IRating(currentIRating, currentIRatingOccurredOn),
        iRatingRecord = iRatingRecord.map { (date, iRating) -> IRating(iRating, date) },
      )

    fun create(
      id: String,
      givenName: String,
      familyName: String,
      code: String?,
      permanentNumber: String?,
      birthDate: String,
      nationality: Nationality,
      infoUrl: String,
      currentElo: Int,
      currentEloOccurredOn: String,
      eloRecord: List<Elo>,
      currentIRating: Int,
      currentIRatingOccurredOn: String,
      iRatingRecord: List<IRating>,
    ) =
      Driver(
        id = DriverId(id),
        fullName = DriverFullName(givenName, familyName),
        code = code?.let { DriverCode(it) },
        permanentNumber = permanentNumber?.let { PermanentNumber(it) },
        birthDate = BirthDate(birthDate),
        nationality = nationality,
        infoUrl = InfoUrl(infoUrl),
        currentElo = Elo(currentElo, currentEloOccurredOn),
        eloRecord = eloRecord,
        currentIRating = IRating(currentIRating, currentIRatingOccurredOn),
        iRatingRecord = iRatingRecord,
      )

    private const val BASE_ELO = 1000
    private const val BASE_IRATING = 1000
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

data class BirthDate(val value: String) : OccurredOn(value)

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
