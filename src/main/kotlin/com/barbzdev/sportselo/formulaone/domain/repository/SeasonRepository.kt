package com.barbzdev.sportselo.formulaone.domain.repository

import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonId
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear

interface SeasonRepository {
  fun getLastSeasonLoaded(): Season?

  fun getLastYearLoaded(): SeasonYear?

  fun getSeasonIdBy(year: SeasonYear): SeasonId?

  fun save(season: Season)

  fun findBy(year: SeasonYear): Season?

  fun findAllSeasonsYears(): List<SeasonYear>
}
