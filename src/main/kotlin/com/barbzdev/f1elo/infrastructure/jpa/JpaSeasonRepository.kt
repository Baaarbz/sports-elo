package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.SeasonYear
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaCircuitDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaConstructorDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverEloHistoryDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaRaceDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaRaceResultDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.SeasonEntity
import jakarta.transaction.Transactional

open class JpaSeasonRepository(
  private val seasonDatasource: JpaSeasonDatasource,
  private val raceDatasource: JpaRaceDatasource,
  private val driverDatasource: JpaDriverDatasource,
  private val constructorDatasource: JpaConstructorDatasource,
  private val circuitDatasource: JpaCircuitDatasource,
  private val raceResultDatasource: JpaRaceResultDatasource,
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource
) : SeasonRepository {

  override fun getLastSeasonLoaded(): Season? = seasonDatasource.findAll().maxByOrNull { it.year }?.gatherAllRelatedDataOfTheSeason()

  override fun findBy(year: SeasonYear): Season? = seasonDatasource.findByYear(year.value)?.gatherAllRelatedDataOfTheSeason()

  private fun SeasonEntity.gatherAllRelatedDataOfTheSeason(): Season {
    val raceEntitiesOfSeason = raceDatasource.findAllBySeason(this)
    val racesOfSeason = mutableListOf<Race>()
    raceEntitiesOfSeason.forEach { raceEntity ->
      val raceResults =
        raceResultDatasource.findAllByRace(raceEntity).map {
          val eloRecordEntity = eloHistoryDatasource.findAllByDriver(it.driver)
          it.toDomain(eloRecordEntity)
        }
      racesOfSeason.add(raceEntity.toDomain(raceResults))
    }
    return this.toDomain().addRacesOfSeason(racesOfSeason)
  }

  @Transactional
  override fun save(season: Season) = season.saveSeason().saveRaces()

  private fun Season.saveSeason(): Season {
    seasonDatasource.save(this.toEntity())
    return this
  }

  private fun Season.saveRaces() {
    races().forEach { race ->
      raceDatasource.save(race.toEntity(this))
      circuitDatasource.save(race.circuit().toEntity())
      race.results().forEach { result ->
        raceResultDatasource.save(result.toEntity(this, race))
        driverDatasource.save(result.driver.toEntity())
        eloHistoryDatasource.saveAll(result.driver.eloRecord().map { it.toEntity(result.driver) })
        constructorDatasource.save(result.constructor.toEntity())
      }
    }
  }
}
