package com.barbzdev.sportselo.core.domain.valueobject

data class Elo(val value: Int, val occurredOn: OccurredOn) {
  init {
    require(value >= 0) { "Elo value cannot be negative" }
  }

  companion object {
    fun create(value: Int, occurredOn: String) = Elo(value, OccurredOn(occurredOn))
  }
}
