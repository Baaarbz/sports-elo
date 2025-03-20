package com.barbzdev.sportselo.core.domain

import com.barbzdev.sportselo.core.domain.valueobject.SportsmanFullName
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId

abstract class Sportsman(
  private val id: SportsmanId,
  private val fullName: SportsmanFullName,
  private val elo: Elo
) {
}

