package com.barbzdev.sportselo.formulaone.infrastructure.jpa

import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.ConstructorId
import com.barbzdev.sportselo.formulaone.domain.repository.ConstructorRepository
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.JpaConstructorDatasource
import kotlin.jvm.optionals.getOrNull

class JpaConstructorRepository(private val jpaConstructorDatasource: JpaConstructorDatasource) : ConstructorRepository {
  override fun findBy(id: ConstructorId): Constructor? =
    jpaConstructorDatasource.findById(id.value).getOrNull()?.toDomain()
}
