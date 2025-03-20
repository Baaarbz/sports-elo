package com.barbzdev.sportselo.formulaone.domain.valueobject.season

import java.util.UUID

data class SeasonId(val value: String) {
  init {
    require(value.isNotBlank())
  }

  companion object {
    fun generate() = SeasonId(UUID.randomUUID().toString())
  }
}
