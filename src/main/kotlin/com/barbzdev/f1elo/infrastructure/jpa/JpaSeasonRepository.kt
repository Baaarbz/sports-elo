package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.SeasonEntity

class JpaSeasonRepository(private val datasource: JpaSeasonDatasource) : SeasonRepository {
  override fun getLastSeasonLoaded(): Season? =
    datasource.findAll().maxByOrNull { it.year }?.let { Season.create(it.year, it.infoUrl) }

  override fun save(season: Season) {
    val seasonEntity =
      SeasonEntity(id = season.id().value, year = season.year().value, infoUrl = season.infoUrl().value)

    datasource.save(seasonEntity)
  }
}
