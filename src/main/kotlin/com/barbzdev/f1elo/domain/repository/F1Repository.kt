package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Race
import com.barbzdev.f1elo.domain.common.Season

interface F1Repository {
  fun gatherRacesBySeason(season: Season): List<Race>
  fun getLastSeasonLoaded(): Season?
}
