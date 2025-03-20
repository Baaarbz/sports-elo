package com.barbzdev.sportselo.formulaone.domain.valueobject.driver

data class PermanentNumber(val value: String) {
  init {
    require(value.isNotBlank()) { "Permanent number cannot be blank" }
    require(value.toIntOrNull() != null) { "Permanent number must be a number" }
    require(value.toInt() > 0) { "Permanent number must be greater than 0" }
  }
}
