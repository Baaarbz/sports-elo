package com.barbzdev.sportselo.infrastructure.mapper

import com.barbzdev.sportselo.domain.Circuit
import com.barbzdev.sportselo.domain.Constructor
import com.barbzdev.sportselo.domain.ConstructorId
import com.barbzdev.sportselo.domain.Driver
import com.barbzdev.sportselo.domain.Race
import com.barbzdev.sportselo.domain.RaceResult
import com.barbzdev.sportselo.domain.Season
import com.barbzdev.sportselo.domain.TheoreticalPerformance
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.domain.common.IRating
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.circuit.CircuitEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor.ConstructorEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor.TheoreticalConstructorPerformanceEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver.DriverEloHistoryEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver.DriverEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver.DriverIRatingHistoryEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.race.RaceEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.race.RaceResultEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.season.SeasonEntity
import java.util.UUID

object DomainToEntityMapper {

  fun Season.toEntity() = SeasonEntity(id = id().value, year = year().value, infoUrl = infoUrl().value)

  fun Race.toEntity(season: Season) =
    RaceEntity(
      id = id().value,
      name = name().value,
      season = SeasonEntity(id = season.id().value, year = season.year().value, infoUrl = season.infoUrl().value),
      round = round().value,
      circuit = circuit().toEntity(),
      infoUrl = infoUrl().value,
      occurredOn = occurredOn().toLocalDate(),
    )

  fun Circuit.toEntity() =
    CircuitEntity(
      id = id().value,
      name = name().value,
      latitude = location().latitude,
      longitude = location().longitude,
      country = country().value,
      locality = locality().value,
      infoUrl = infoUrl().value)

  fun RaceResult.toEntity(season: Season, race: Race) =
    RaceResultEntity(
      id = id,
      race =
        RaceEntity(
          id = race.id().value,
          name = race.name().value,
          season = SeasonEntity(id = season.id().value, year = season.year().value, infoUrl = season.infoUrl().value),
          round = race.round().value,
          circuit = race.circuit().toEntity(),
          infoUrl = race.infoUrl().value,
          occurredOn = race.occurredOn().toLocalDate(),
        ),
      driver = driver.toEntity(),
      position = position,
      number = number,
      constructor = constructor.toEntity(),
      points = points,
      grid = grid,
      laps = laps,
      status = status.text,
      timeInMillis = timeInMillis,
      fastestLapInMillis = fastestLapInMillis,
      averageSpeed = averageSpeed,
      averageSpeedUnit = averageSpeedUnit)

  fun Constructor.toEntity() =
    ConstructorEntity(
      id = id().value, name = name().value, nationality = nationality().countryCode, infoUrl = infoUrl().value)

  fun Driver.toEntity() =
    DriverEntity(
      id = id().value,
      familyName = fullName().familyName,
      givenName = fullName().givenName,
      code = code()?.value,
      permanentNumber = permanentNumber()?.value,
      birthDate = birthDate().toLocalDate(),
      nationality = nationality().countryCode,
      infoUrl = infoUrl().value,
      currentElo = currentElo().value,
      currentEloOccurredOn = currentElo().toLocalDate(),
      currentIRating = currentIRating().value,
      currentIRatingOccurredOn = currentIRating().toLocalDate())

  fun Elo.toEntity(driver: Driver) =
    DriverEloHistoryEntity(elo = value, occurredOn = toLocalDate(), driver = driver.toEntity())

  fun IRating.toEntity(driver: Driver) =
    DriverIRatingHistoryEntity(iRating = value, occurredOn = toLocalDate(), driver = driver.toEntity())

  fun mapToTheoreticalConstructorPerformanceEntity(
    theoreticalPerformance: TheoreticalPerformance,
    constructor: ConstructorEntity,
    season: SeasonEntity
  ) =
    TheoreticalConstructorPerformanceEntity(
      id = UUID.randomUUID().toString(),
      constructor = constructor,
      season = season,
      theoreticalPerformance =
        theoreticalPerformance.getConstructorPerformance(ConstructorId(constructor.id))!!.performance,
      isAnalyzedSeason = theoreticalPerformance.isAnalyzedSeason(),
      dataOriginSource = theoreticalPerformance.dataOrigin()?.source,
      dataOriginUrl = theoreticalPerformance.dataOrigin()?.url,
    )
}
