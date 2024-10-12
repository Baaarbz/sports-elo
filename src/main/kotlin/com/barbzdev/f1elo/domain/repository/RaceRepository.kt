package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Race

interface RaceRepository {
  fun save(race: Race)
}
