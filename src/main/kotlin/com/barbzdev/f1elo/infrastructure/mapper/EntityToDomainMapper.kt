package com.barbzdev.f1elo.infrastructure.mapper

import com.barbzdev.f1elo.domain.Circuit
import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.ConstructorPerformance
import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.RaceResult
import com.barbzdev.f1elo.domain.RaceResultStatus
import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.domain.common.Elo
import com.barbzdev.f1elo.domain.common.Nationality
import com.barbzdev.f1elo.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.circuit.CircuitEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.ConstructorEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.TheoreticalConstructorPerformanceEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.driver.DriverEloHistoryEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.driver.DriverEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.race.RaceEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.race.RaceResultEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.season.SeasonEntity

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

  fun RaceResultEntity.toDomain(eloRecord: List<DriverEloHistoryEntity>) =
    RaceResult(
      id = id,
      driver = driver.toDomain(eloRecord),
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

  fun DriverEntity.toDomain(eloRecord: List<DriverEloHistoryEntity>) =
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
      eloRecord = eloRecord.map { it.toDomain() })

  private fun DriverEloHistoryEntity.toDomain() = Elo(rating = elo, occurredOn = occurredOn.toString())

  fun List<TheoreticalConstructorPerformanceEntity>.toDomain(): TheoreticalPerformance? =
    if (isEmpty()) null
    else
      TheoreticalPerformance.create(
        isAnalyzedSeason = first().isAnalyzedSeason,
        seasonYear = first().season.year,
        constructorsPerformance = map { ConstructorPerformance(it.constructor.toDomain(), it.theoreticalPerformance) })
}
