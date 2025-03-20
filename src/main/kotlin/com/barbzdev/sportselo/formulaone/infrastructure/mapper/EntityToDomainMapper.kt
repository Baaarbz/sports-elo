package com.barbzdev.sportselo.formulaone.infrastructure.mapper

import com.barbzdev.sportselo.formulaone.domain.Circuit
import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.ConstructorPerformance
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.domain.RaceResult
import com.barbzdev.sportselo.domain.RaceResultStatus
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.TheoreticalPerformance
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.circuit.CircuitEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.ConstructorEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.TheoreticalConstructorPerformanceEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEloHistoryEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverIRatingHistoryEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.RaceEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.RaceResultEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.SeasonEntity

object EntityToDomainMapper {
  fun SeasonEntity.toDomain() = Season.create(id = id, year = year, infoUrl = infoUrl)

  fun RaceEntity.toDomain(results: List<RaceResult>) =
    Race.create(
      id = id,
      name = name,
      round = round,
      circuit = circuit.toDomain(),
      infoUrl = infoUrl,
      occurredOn = occurredOn.toString(),
      results = results)

  fun CircuitEntity.toDomain() =
    Circuit.create(
      id = id,
      name = name,
      country = country,
      locality = locality,
      infoUrl = infoUrl,
      latitude = latitude,
      longitude = longitude)

  fun RaceResultEntity.toDomain(
    eloRecord: List<DriverEloHistoryEntity>,
    iRatingRecord: List<DriverIRatingHistoryEntity>
  ) =
    RaceResult(
      id = id,
      driver = driver.toDomain(eloRecord, iRatingRecord),
      position = position,
      constructor = constructor.toDomain(),
      number = number,
      points = points,
      grid = grid,
      laps = laps,
      status = RaceResultStatus.fromText(status),
      timeInMillis = timeInMillis,
      fastestLapInMillis = fastestLapInMillis,
      averageSpeed = averageSpeed,
      averageSpeedUnit = averageSpeedUnit,
    )

  fun ConstructorEntity.toDomain() =
    Constructor.create(id = id, name = name, nationality = Nationality.fromCountryCode(nationality), infoUrl = infoUrl)

  fun DriverEntity.toDomain(eloRecord: List<DriverEloHistoryEntity>, iRatingRecord: List<DriverIRatingHistoryEntity>) =
    Driver.create(
      id = id,
      familyName = familyName,
      givenName = givenName,
      code = code,
      permanentNumber = permanentNumber,
      birthDate = birthDate.toString(),
      nationality = Nationality.fromCountryCode(nationality),
      infoUrl = infoUrl,
      currentElo = currentElo,
      currentEloOccurredOn = currentEloOccurredOn.toString(),
      eloRecord = eloRecord.map { it.toDomain() },
      currentIRating = currentIRating,
      currentIRatingOccurredOn = currentIRatingOccurredOn.toString(),
      iRatingRecord = iRatingRecord.map { it.toDomain() })

  private fun DriverEloHistoryEntity.toDomain() = Elo(value = elo, occurredOn = occurredOn.toString())
}
