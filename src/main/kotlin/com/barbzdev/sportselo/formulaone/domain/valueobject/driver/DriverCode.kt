package com.barbzdev.sportselo.formulaone.domain.valueobject.driver

data class DriverCode(val value: String) {
  init {
    require(value.isNotBlank()) { "DriverCode cannot be blank" }
  }
}
