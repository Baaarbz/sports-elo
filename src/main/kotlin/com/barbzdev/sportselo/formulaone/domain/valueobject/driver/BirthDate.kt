package com.barbzdev.sportselo.formulaone.domain.valueobject.driver

import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn

data class BirthDate(val date: OccurredOn) {
  init {
    require(date.value.isNotBlank()) { "Birth date cannot be blank" }
  }

  companion object {
    fun create(value: String): BirthDate {
      return BirthDate(OccurredOn(value))
    }
  }
}
