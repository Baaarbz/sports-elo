package com.barbzdev.sportselo.formulaone.domain.valueobject.race

import java.util.UUID

data class RaceId(val value: String) {
  init {
    require(value.isNotBlank())
  }

  companion object {
    fun generate() = RaceId(UUID.randomUUID().toString())
  }
}
