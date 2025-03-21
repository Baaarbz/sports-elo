package com.barbzdev.sportselo.formulaone.infrastructure.jpa

import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.ConstructorId
import com.barbzdev.sportselo.formulaone.domain.repository.ConstructorRepository
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.ConstructorEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.ConstructorMapper
import kotlin.jvm.optionals.getOrNull

class JpaConstructorRepository(private val jpaConstructorDatasource: JpaConstructorDatasource, private val constructorMapper: ConstructorMapper) : ConstructorRepository {
  override fun findBy(id: ConstructorId): Constructor? =
    jpaConstructorDatasource.findById(id.value).getOrNull()?.toDomain()

  private fun ConstructorEntity.toDomain() = constructorMapper.toDomain(this)
}
