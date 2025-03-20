package com.barbzdev.sportselo.domain.common

data class DomainPaginated<T>(
  val elements: List<T> = emptyList(),
  val page: Int = 0,
  val pageSize: Int = 25,
  val totalElements: Long = 0,
  val totalPages: Int = 0
)

data class Page(val value: Int)

data class PageSize(val value: Int)

data class SortBy(val value: String)

data class SortOrder(val value: String)
