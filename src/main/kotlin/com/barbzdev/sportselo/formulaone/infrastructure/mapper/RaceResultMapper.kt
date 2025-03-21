package com.barbzdev.sportselo.formulaone.infrastructure.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResult
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResultStatus
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.RaceResultEntity

class RaceResultMapper(
  private val driverMapper: DriverMapper,
  private val constructorMapper: ConstructorMapper,
  private val raceMapper: RaceMapper
) : EntityMapper<RaceResult, RaceResultEntity> {

  override fun toEntity(domain: RaceResult): RaceResultEntity {
    return RaceResultEntity(
      id = domain.id,
      driver = driverMapper.toEntity(domain.driver),
      constructor = constructorMapper.toEntity(domain.constructor),
      grid = domain.grid,
      laps = domain.laps,
      number = domain.number,
      points = domain.points,
      position = domain.position,
      status = domain.status.text,
      timeInMillis = domain.timeInMillis,
      race = raceMapper.toEntity(domain.race),
      fastestLapInMillis = domain.fastestLapInMillis,
      averageSpeed = domain.averageSpeed,
      averageSpeedUnit = domain.averageSpeedUnit
    )
  }

  override fun toDomain(entity: RaceResultEntity): RaceResult {
    return RaceResult(
      id = entity.id,
      driver = driverMapper.toDomain(entity.driver),
      constructor = constructorMapper.toDomain(entity.constructor),
      grid = entity.grid,
      laps = entity.laps,
      number = entity.number,
      points = entity.points,
      position = entity.position,
      status = RaceResultStatus.fromText(entity.status),
      timeInMillis = entity.timeInMillis,
      fastestLapInMillis = entity.fastestLapInMillis,
      averageSpeed = entity.averageSpeed,
      averageSpeedUnit = entity.averageSpeedUnit,
    )
  }
}
