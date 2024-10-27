package com.barbzdev.f1elo.infrastructure.mapper

import com.barbzdev.f1elo.domain.Circuit
import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.RaceResult
import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.common.Elo
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.CircuitEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.ConstructorEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.DriverEloHistoryEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.DriverEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.RaceEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.RaceResultEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.SeasonEntity

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
      currentElo = currentElo().rating,
      currentEloOccurredOn = currentElo().toLocalDate())

  fun Elo.toEntity(driver: Driver) =
    DriverEloHistoryEntity(
      elo = rating,
      occurredOn = toLocalDate(),
      driver =
        DriverEntity(
          id = driver.id().value,
          familyName = driver.fullName().familyName,
          givenName = driver.fullName().givenName,
          code = driver.code()?.value,
          permanentNumber = driver.permanentNumber()?.value,
          birthDate = driver.birthDate().toLocalDate(),
          nationality = driver.nationality().countryCode,
          infoUrl = driver.infoUrl().value,
          currentElo = driver.currentElo().rating,
          currentEloOccurredOn = driver.currentElo().toLocalDate()))
}
