package com.barbzdev.sportselo.core.domain.valueobject

data class SportsmanId(val value: String) {
  init {
    require(value.isNotBlank()) { "DriverId cannot be blank" }
  }
}
