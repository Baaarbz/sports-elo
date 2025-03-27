package com.barbzdev.sportselo.core.domain.util

data class DomainPaginated<T>(
  val elements: List<T> = emptyList(),
  val page: Int = 0,
  val pageSize: Int = 25,
  val totalElements: Long = 0,
  val totalPages: Int = 0
)

data class Page(val value: Int)

data class PageSize(val value: Int)

data class SortBy(val value: String) {
  init {
    when (value) {
      "currentElo", "highestElo", "lowestElo", "id" -> {}
      else -> throw IllegalArgumentException("Invalid sortBy value for find all drivers query")
    }
  }
}

data class SortOrder(val value: String)
