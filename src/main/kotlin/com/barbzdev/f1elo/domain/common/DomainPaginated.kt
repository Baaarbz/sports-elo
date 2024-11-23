package com.barbzdev.f1elo.domain.common

data class DomainPaginated<T> (
  val elements: List<T> = emptyList(),
  val page: Int = 0,
  val pageSize: Int = 25,
  val totalElements: Long = 0,
  val totalPages: Int = 0
)
