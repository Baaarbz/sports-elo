package com.barbzdev.sportselo.formulaone.domain.valueobject.race

data class RaceName(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
