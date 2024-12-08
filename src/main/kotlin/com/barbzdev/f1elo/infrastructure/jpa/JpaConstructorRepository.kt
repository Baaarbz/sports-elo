package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.ConstructorId
import com.barbzdev.f1elo.domain.repository.ConstructorRepository
import com.barbzdev.f1elo.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import kotlin.jvm.optionals.getOrNull

class JpaConstructorRepository(private val jpaConstructorDatasource: JpaConstructorDatasource) : ConstructorRepository {
  override fun findBy(id: ConstructorId): Constructor? =
    jpaConstructorDatasource.findById(id.value).getOrNull()?.toDomain()
}
