package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.RaceEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.RaceResultEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.SeasonEntity

class SeasonMapper(
  private val circuitMapper: CircuitMapper,
  private val driverMapper: DriverMapper,
  private val constructorMapper: ConstructorMapper
) : EntityMapper<Season, SeasonEntity> {

  override fun toEntity(domain: Season): SeasonEntity {
    val seasonEntity =
      SeasonEntity(
        id = domain.id().value, year = domain.year().value, infoUrl = domain.infoUrl().value, races = emptyList())

    val racesEntityMap = mutableMapOf<String, RaceEntity>()
    val racesEntity =
      domain.races().map { race ->
        val raceEntity =
          RaceEntity(
            id = race.id().value,
            season = seasonEntity,
            round = race.round().value,
            circuit = circuitMapper.toEntity(race.circuit()),
            infoUrl = race.infoUrl().value,
            occurredOn = race.occurredOn().date.toLocalDate(),
            name = race.name().value,
            raceResults = emptyList())
        racesEntityMap[race.id().value] = raceEntity
        raceEntity
      }

    val raceResultsEntity =
      domain.races().flatMap { race ->
        race.results().map { raceResult ->
          RaceResultEntity(
            id = raceResult.id,
            driver = raceResult.driver.let { driver -> driverMapper.toEntity(driver) },
            constructor = constructorMapper.toEntity(raceResult.constructor),
            grid = raceResult.grid,
            laps = raceResult.laps,
            number = raceResult.number,
            points = raceResult.points,
            position = raceResult.position,
            status = raceResult.status.text,
            timeInMillis = raceResult.timeInMillis,
            race = racesEntityMap[race.id().value]!!,
            fastestLapInMillis = raceResult.fastestLapInMillis,
            averageSpeed = raceResult.averageSpeed,
            averageSpeedUnit = raceResult.averageSpeedUnit,
          )
        }
      }
    val updatedRaceEntity =
      racesEntity.map { raceEntity ->
        val raceResultsForThisRace = raceResultsEntity.filter { it.race.id == raceEntity.id }
        raceEntity.copy(raceResults = raceResultsForThisRace)
      }

    return seasonEntity.copy(races = updatedRaceEntity)
  }

  override fun toDomain(entity: SeasonEntity): Season {
    return Season.create(id = entity.id, year = entity.year, infoUrl = entity.infoUrl)
  }
}
