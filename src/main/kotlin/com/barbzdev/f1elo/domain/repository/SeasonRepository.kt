package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Season
import com.barbzdev.f1elo.domain.common.SeasonId
import com.barbzdev.f1elo.domain.common.SeasonYear

interface SeasonRepository {
  fun getLastSeasonLoaded(): Season?

  fun getLastYearLoaded(): SeasonYear?

  fun getSeasonIdBy(year: SeasonYear): SeasonId?

  fun save(season: Season)

  fun findBy(year: SeasonYear): Season?
}
