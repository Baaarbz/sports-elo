package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.repository.SeasonRepository
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaSeasonDatasource

class JpaSeasonRepository(
  private val datasource: JpaSeasonDatasource
) : SeasonRepository {
  override fun getLastSeasonLoaded(): Season? {
    TODO("Not yet implemented")
  }

  override fun save(season: Season) {
    TODO("Not yet implemented")
  }
}
