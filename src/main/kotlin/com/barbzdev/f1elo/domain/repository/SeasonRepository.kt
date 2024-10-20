package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Season

interface SeasonRepository {
  fun getLastSeasonLoaded(): Season?
  fun save(season: Season)
}
