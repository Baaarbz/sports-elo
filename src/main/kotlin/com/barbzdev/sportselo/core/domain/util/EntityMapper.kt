package com.barbzdev.sportselo.core.domain.util

interface EntityMapper<D, E> {
  fun toEntity(domain: D): E
  fun toDomain(entity: E): D
}
