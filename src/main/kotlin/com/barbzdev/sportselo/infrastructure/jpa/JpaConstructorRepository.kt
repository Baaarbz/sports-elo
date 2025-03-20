package com.barbzdev.sportselo.infrastructure.jpa

import com.barbzdev.sportselo.domain.Constructor
import com.barbzdev.sportselo.domain.ConstructorId
import com.barbzdev.sportselo.domain.repository.ConstructorRepository
import com.barbzdev.sportselo.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import kotlin.jvm.optionals.getOrNull

class JpaConstructorRepository(private val jpaConstructorDatasource: JpaConstructorDatasource) : ConstructorRepository {
  override fun findBy(id: ConstructorId): Constructor? =
    jpaConstructorDatasource.findById(id.value).getOrNull()?.toDomain()
}
