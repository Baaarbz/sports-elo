package com.barbzdev.f1elo.domain.common

import java.time.LocalDate

abstract class OccurredOn(val date: String) {
  init {
    require(date.isNotBlank()) { "OccurredOn cannot be blank" }
    require(isValidDate(date)) { "Invalid date format. YYYY-MM-DD" }
  }

  fun toLocalDate(): LocalDate = LocalDate.parse(date)

  private fun isValidDate(date: String): Boolean = dateRegex.matches(date)

  private companion object {
    val dateRegex = "^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$".toRegex()
  }
}
