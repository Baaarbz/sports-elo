package com.barbzdev.sportselo.formulaone.infrastructure.jpa

import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonId
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.circuit.JpaCircuitDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverEloHistoryDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverIRatingHistoryDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.JpaRaceDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.JpaRaceResultDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.JpaSeasonDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.SeasonEntity
import jakarta.transaction.Transactional

open class JpaSeasonRepository(
  private val seasonDatasource: JpaSeasonDatasource,
  private val raceDatasource: JpaRaceDatasource,
  private val driverDatasource: JpaDriverDatasource,
  private val constructorDatasource: JpaConstructorDatasource,
  private val circuitDatasource: JpaCircuitDatasource,
  private val raceResultDatasource: JpaRaceResultDatasource,
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource,
  private val iRatingHistoryDatasource: JpaDriverIRatingHistoryDatasource
) : SeasonRepository {

  override fun getLastSeasonLoaded(): Season? =
    seasonDatasource.findAll().maxByOrNull { it.year }?.gatherAllRelatedDataOfTheSeason()

  override fun getLastYearLoaded(): SeasonYear? =
    seasonDatasource
      .findAll()
      .maxByOrNull { it.year }
      ?.let {
        return SeasonYear(it.year)
      }

  override fun getSeasonIdBy(year: SeasonYear): SeasonId? =
    seasonDatasource.findByYear(year.value)?.let { SeasonId(it.id) }

  override fun findBy(year: SeasonYear): Season? =
    seasonDatasource.findByYear(year.value)?.gatherAllRelatedDataOfTheSeason()

  private fun SeasonEntity.gatherAllRelatedDataOfTheSeason(): Season {
    val raceEntitiesOfSeason = raceDatasource.findAllBySeason(this)
    val racesOfSeason = mutableListOf<Race>()
    raceEntitiesOfSeason.forEach { raceEntity ->
      val raceResults =
        raceResultDatasource.findAllByRace(raceEntity).map {
          val eloRecordEntity = eloHistoryDatasource.findAllByDriver(it.driver)
          val iRatingRecordEntity = iRatingHistoryDatasource.findAllByDriver(it.driver)
          it.toDomain(eloRecordEntity, iRatingRecordEntity)
        }
      racesOfSeason.add(raceEntity.toDomain(raceResults))
    }
    return this.toDomain().addRacesOfSeason(racesOfSeason)
  }

  override fun findAllSeasonsYears(): List<SeasonYear> = seasonDatasource.findAll().map { SeasonYear(it.year) }

  @Transactional override fun save(season: Season) = season.saveSeason().saveRaces()

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
        iRatingHistoryDatasource.saveAll(result.driver.iRatingRecord().map { it.toEntity(result.driver) })
        constructorDatasource.save(result.constructor.toEntity())
      }
    }
  }
}
