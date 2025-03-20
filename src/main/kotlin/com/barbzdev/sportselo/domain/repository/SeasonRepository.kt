package com.barbzdev.sportselo.domain.repository

import com.barbzdev.sportselo.domain.Season
import com.barbzdev.sportselo.domain.common.SeasonId
import com.barbzdev.sportselo.domain.common.SeasonYear

interface SeasonRepository {
  fun getLastSeasonLoaded(): Season?

  fun getLastYearLoaded(): SeasonYear?

  fun getSeasonIdBy(year: SeasonYear): SeasonId?

  fun save(season: Season)

  fun findBy(year: SeasonYear): Season?

  fun findAllSeasonsYears(): List<SeasonYear>
}
