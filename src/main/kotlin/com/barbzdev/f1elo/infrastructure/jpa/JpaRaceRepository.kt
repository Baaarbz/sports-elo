package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.repository.RaceRepository
import com.barbzdev.f1elo.infrastructure.spring.repository.JpaRaceDatasource

class JpaRaceRepository(
  private val datasource: JpaRaceDatasource
) : RaceRepository {
  override fun saveAll(races: List<Race>) {
    TODO("Not yet implemented")
  }
}
