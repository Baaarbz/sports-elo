package com.barbzdev.sportselo.formulaone.domain.valueobject.race

import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn

data class RaceDate(val date: OccurredOn) {
  init {
    require(date.value.isNotBlank())
  }

  companion object {
    fun create(value: String): RaceDate {
      return RaceDate(OccurredOn(value))
    }
  }
}
