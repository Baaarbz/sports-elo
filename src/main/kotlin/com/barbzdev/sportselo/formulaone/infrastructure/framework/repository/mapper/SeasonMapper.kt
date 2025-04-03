package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.Circuit
import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResult
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResultStatus
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.CircuitEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.ConstructorEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.RaceEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.RaceResultEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.SeasonEntity

class SeasonMapper(
  private val circuitMapper: CircuitMapper,
  private val driverMapper: DriverMapper,
  private val constructorMapper: ConstructorMapper
) : EntityMapper<Season, SeasonEntity> {

  override fun toEntity(domain: Season): SeasonEntity {
    val driverCache = mutableMapOf<String, DriverEntity>()
    val constructorCache = mutableMapOf<String, ConstructorEntity>()
    val circuitCache = mutableMapOf<String, CircuitEntity>()

    val seasonEntity =
      SeasonEntity(
        id = domain.id().value, year = domain.year().value, infoUrl = domain.infoUrl().value, races = emptyList())

    val racesEntityMap = mutableMapOf<String, RaceEntity>()
    val racesEntity =
      domain.races().map { race ->
        val circuitEntity =
          circuitCache.getOrPut(race.circuit().id().value) {
            circuitMapper.toEntity(race.circuit())
          }
        val raceEntity =
          RaceEntity(
            id = race.id().value,
            season = seasonEntity,
            round = race.round().value,
            circuit = circuitEntity,
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
          val driverEntity =
            driverCache.getOrPut(raceResult.driver.id().value) { driverMapper.toEntity(raceResult.driver) }

          val constructorEntity =
            constructorCache.getOrPut(raceResult.constructor.id().value) {
              constructorMapper.toEntity(raceResult.constructor)
            }

          RaceResultEntity(
            id = raceResult.id,
            driver = driverEntity,
            constructor = constructorEntity,
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
    val circuitCache = mutableMapOf<String, Circuit>()
    val driverCache = mutableMapOf<String, Driver>()
    val constructorCache = mutableMapOf<String, Constructor>()

    val season = Season.create(id = entity.id, year = entity.year, infoUrl = entity.infoUrl)

    val races = mutableListOf<Race>()
    entity.races.forEach { raceEntity ->
      val circuit = circuitCache.getOrPut(raceEntity.circuit.id) { circuitMapper.toDomain(raceEntity.circuit) }

      val race =
        Race.create(
          id = raceEntity.id,
          round = raceEntity.round,
          name = raceEntity.name,
          infoUrl = raceEntity.infoUrl,
          occurredOn = raceEntity.occurredOn.toString(),
          circuit = circuit,
          results = emptyList())

      val raceResults = mutableListOf<RaceResult>()

      raceEntity.raceResults.forEach { resultEntity ->
        val driver = driverCache.getOrPut(resultEntity.driver.id) { driverMapper.toDomain(resultEntity.driver) }

        val constructor =
          constructorCache.getOrPut(resultEntity.constructor.id) {
            constructorMapper.toDomain(resultEntity.constructor)
          }

        val raceResult =
          RaceResult(
            id = resultEntity.id,
            driver = driver,
            constructor = constructor,
            position = resultEntity.position,
            number = resultEntity.number,
            grid = resultEntity.grid,
            points = resultEntity.points,
            laps = resultEntity.laps,
            status = RaceResultStatus.fromText(resultEntity.status),
            timeInMillis = resultEntity.timeInMillis,
            fastestLapInMillis = resultEntity.fastestLapInMillis,
            averageSpeed = resultEntity.averageSpeed,
            averageSpeedUnit = resultEntity.averageSpeedUnit)
        raceResults.add(raceResult)
      }
      val sortedResults = raceResults.sortedBy { it.position }
      val updatedRace = race.addResults(sortedResults)
      races.add(updatedRace)
    }

    val sortedRaces = races.sortedBy { it.occurredOn().date.toLocalDate() }

    return season.addRacesOfSeason(sortedRaces)
  }
}
