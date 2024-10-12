package com.barbzdev.f1elo.domain.common

abstract class OccurredOn(val value: String) {
  init {
    require(value.isNotBlank()) { "OccurredOn cannot be blank" }
    require(isValidDate(value)) { "Invalid date format. YYYY-MM-DD" }
  }

  private fun isValidDate(date: String): Boolean = dateRegex.matches(date)

  private companion object {
    val dateRegex = "^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$".toRegex()
  }
}
