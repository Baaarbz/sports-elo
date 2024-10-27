package com.barbzdev.f1elo.application

import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.domain.repository.SeasonRepository

class CalculateEloOfDriversBySeasonUseCase(
  private val seasonRepository: SeasonRepository,
  private val driverRepository: DriverRepository
) {
  operator fun invoke(request: CalculateEloOfDriversBySeasonRequest) {
    TODO("Not yet implemented")
  }
}

data class CalculateEloOfDriversBySeasonRequest(val season: Int)
