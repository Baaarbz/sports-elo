package com.barbzdev.sportselo.domain.common

import java.util.UUID

data class SeasonId(val value: String) {
  init {
    require(value.isNotBlank())
  }

  companion object {
    fun generate() = SeasonId(UUID.randomUUID().toString())
  }
}
