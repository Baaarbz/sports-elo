package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season

import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonId
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper.SeasonMapper
import jakarta.transaction.Transactional

open class JpaSeasonRepository(
  private val seasonDatasource: JpaSeasonDatasource,
  private val seasonMapper: SeasonMapper
) : SeasonRepository {

  override fun getLastSeasonLoaded(): Season? {
    val seasonWithRaces = seasonDatasource.findLastSeasonWithRaces() ?: return null

    return fetchRaceResults(seasonWithRaces)
  }

  override fun getLastYearLoaded(): SeasonYear? =
    seasonDatasource.findLastSeasonWithRaces()?.let { SeasonYear(it.year) }

  override fun getSeasonIdBy(year: SeasonYear): SeasonId? =
    seasonDatasource.findByYearWithRaces(year.value)?.let { SeasonId(it.id) }

  override fun findBy(year: SeasonYear): Season? {
    val seasonWithRaces = seasonDatasource.findByYearWithRaces(year.value) ?: return null

    return fetchRaceResults(seasonWithRaces)
  }

  override fun findAllSeasonsYears(): List<SeasonYear> = seasonDatasource.findAll().map { SeasonYear(it.year) }

  private fun fetchRaceResults(seasonWithRaces: SeasonEntity): Season {
    val racesWithResults = seasonDatasource.findRaceResultsBySeasonId(seasonWithRaces.id)

    val raceMap = racesWithResults.associateBy { it.id }

    val updatedRaces = seasonWithRaces.races.map { raceMap[it.id] ?: it }
    val fullSeason = seasonWithRaces.copy(races = updatedRaces)

    return seasonMapper.toDomain(fullSeason)
  }

  @Transactional
  override fun save(season: Season) {
    seasonDatasource.saveAndFlush(seasonMapper.toEntity(season))
  }
}
