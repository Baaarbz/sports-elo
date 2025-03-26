package com.barbzdev.sportselo.formulaone.domain

import com.barbzdev.sportselo.core.domain.Sportsman
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanFullName
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId
import com.barbzdev.sportselo.formulaone.domain.valueobject.InfoUrl
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.BirthDate
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.DriverCode
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.PermanentNumber

class Driver
private constructor(
  private val id: SportsmanId,
  private val fullName: SportsmanFullName,
  private val currentElo: Elo,
  private val eloRecord: List<Elo>,
  private val code: DriverCode?,
  private val permanentNumber: PermanentNumber?,
  private val birthDate: BirthDate,
  private val nationality: Nationality,
  private val infoUrl: InfoUrl,
) :
  Sportsman(
    id = id,
    fullName = fullName,
    currentElo = currentElo,
    eloRecord = eloRecord,
  ) {

  fun code() = code

  fun permanentNumber() = permanentNumber

  fun birthDate() = birthDate

  fun nationality() = nationality

  fun infoUrl() = infoUrl

  override fun updateElo(value: Int, occurredOn: String): Driver {
    var adjustedValue = value
    if (adjustedValue < 0) {
      adjustedValue = 0
    }
    val newElo = Elo.create(adjustedValue, occurredOn)
    return Driver(
      id = id,
      fullName = fullName,
      currentElo = newElo,
      eloRecord = eloRecord.plus(newElo),
      code = code,
      permanentNumber = permanentNumber,
      birthDate = birthDate,
      nationality = nationality,
      infoUrl = infoUrl,
    )
  }

  override fun resetElo(): Driver {
    val resetElo = Elo(BASE_ELO, eloRecord().first().occurredOn)
    return Driver(
      id = id,
      fullName = fullName,
      currentElo = resetElo,
      eloRecord = listOf(resetElo),
      code = code,
      permanentNumber = permanentNumber,
      birthDate = birthDate,
      nationality = nationality,
      infoUrl = infoUrl,
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Driver

    if (id != other.id) return false
    if (fullName != other.fullName) return false
    if (currentElo != other.currentElo) return false
    if (eloRecord != other.eloRecord) return false
    if (code != other.code) return false
    if (permanentNumber != other.permanentNumber) return false
    if (birthDate != other.birthDate) return false
    if (nationality != other.nationality) return false
    if (infoUrl != other.infoUrl) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + fullName.hashCode()
    result = 31 * result + currentElo.hashCode()
    result = 31 * result + eloRecord.hashCode()
    result = 31 * result + (code?.hashCode() ?: 0)
    result = 31 * result + (permanentNumber?.hashCode() ?: 0)
    result = 31 * result + birthDate.hashCode()
    result = 31 * result + nationality.hashCode()
    result = 31 * result + infoUrl.hashCode()
    return result
  }

  override fun toString(): String {
    return "Driver(id=$id, fullName=$fullName, currentElo=$currentElo, eloRecord=$eloRecord, code=$code, permanentNumber=$permanentNumber, birthDate=$birthDate, nationality=$nationality, infoUrl=$infoUrl)"
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
    ): Driver {
      val startingElo = Elo.create(BASE_ELO, debutDate)
      return Driver(
        id = SportsmanId(id),
        fullName = SportsmanFullName(givenName, familyName),
        code = code?.let { DriverCode(it) },
        permanentNumber = permanentNumber?.let { PermanentNumber(it) },
        birthDate = BirthDate.create(birthDate),
        nationality = Nationality.fromGentilic(nationality),
        infoUrl = InfoUrl(infoUrl),
        currentElo = startingElo,
        eloRecord = listOf(startingElo),
      )
    }

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
    ): Driver {
      val elo = Elo.create(currentElo, currentEloOccurredOn)
      return Driver(
        id = SportsmanId(id),
        fullName = SportsmanFullName(givenName, familyName),
        code = code?.let { DriverCode(it) },
        permanentNumber = permanentNumber?.let { PermanentNumber(it) },
        birthDate = BirthDate.create(birthDate),
        nationality = nationality,
        infoUrl = InfoUrl(infoUrl),
        currentElo = elo,
        eloRecord = eloRecord,
      )
    }
  }
}
