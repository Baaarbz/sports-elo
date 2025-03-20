package com.barbzdev.sportselo.core.domain.valueobject

data class SportsmanFullName(val givenName: String, val familyName: String) {
  init {
    require(givenName.isNotBlank()) { "Given name cannot be blank" }
    require(familyName.isNotBlank()) { "Family name cannot be blank" }
  }
}
