package com.barbzdev.sportselo.formulaone.domain.valueobject.race

data class Round(val value: Int) {
  init {
    require(value > 0) { "Round must be greater than 0" }
  }
}
