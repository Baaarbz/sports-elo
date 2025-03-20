package com.barbzdev.sportselo.core.domain

import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanFullName
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId

abstract class Sportsman(
  private val id: SportsmanId,
  private val fullName: SportsmanFullName,
  private val currentElo: Elo,
  private val eloRecord: List<Elo>
) {
  fun id() = id

  fun fullName() = fullName

  fun currentElo() = currentElo

  fun eloRecord() = eloRecord.sortedBy { it.occurredOn.toLocalDate() }

  fun highestElo(): Elo = eloRecord.maxByOrNull { it.value }!!

  fun lowestElo(): Elo = eloRecord.minByOrNull { it.value }!!

  abstract fun updateElo(value: Int, occurredOn: String): Sportsman

  abstract fun resetElo(): Sportsman

  companion object {
    const val BASE_ELO = 1_000
  }
}

