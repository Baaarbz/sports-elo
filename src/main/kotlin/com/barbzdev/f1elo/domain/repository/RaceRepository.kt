package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Race

interface RaceRepository {
  fun saveAll(races: List<Race>)
}
