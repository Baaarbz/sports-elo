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

  override fun getLastSeasonLoaded(): Season? =
    seasonDatasource.findAll().maxByOrNull { it.year }?.let { seasonMapper.toDomain(it) }

  override fun getLastYearLoaded(): SeasonYear? =
    seasonDatasource.findAll().maxByOrNull { it.year }?.let { SeasonYear(it.year) }

  override fun getSeasonIdBy(year: SeasonYear): SeasonId? =
    seasonDatasource.findByYear(year.value)?.let { SeasonId(it.id) }

  override fun findBy(year: SeasonYear): Season? =
    seasonDatasource.findByYear(year.value)?.let { seasonMapper.toDomain(it) }

  override fun findAllSeasonsYears(): List<SeasonYear> = seasonDatasource.findAll().map { SeasonYear(it.year) }

  @Transactional
  override fun save(season: Season) {
    seasonDatasource.save(seasonMapper.toEntity(season))
  }
}
