package com.barbzdev.sportselo.core.domain.valueobject

import java.time.LocalDate

data class OccurredOn(val value: String) {
  init {
    require(value.isNotBlank()) { "OccurredOn cannot be blank" }
    require(isValidDate(value)) { "Invalid date format. YYYY-MM-DD" }
  }

  fun toLocalDate(): LocalDate = LocalDate.parse(value)

  private fun isValidDate(date: String): Boolean = dateRegex.matches(date)

  companion object {
    private val dateRegex = "^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$".toRegex()
    fun from(date: LocalDate): OccurredOn = OccurredOn(date.toString())
  }
}
