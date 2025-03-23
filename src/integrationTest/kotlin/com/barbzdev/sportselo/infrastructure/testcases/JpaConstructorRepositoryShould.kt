package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.formulaone.factory.ConstructorFactory.aConstructor
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class JpaConstructorRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaConstructorRepository
  @Autowired private lateinit var datasource: JpaConstructorDatasource

  @Test
  fun `find a constructor by id`() {
    val constructorInDatabase = givenAConstructorInDatabase()

    val constructor = repository.findBy(constructorInDatabase.id())

    assertThat(constructor).isEqualTo(constructorInDatabase)
  }

  private fun givenAConstructorInDatabase() = aConstructor().also { datasource.save(it.toEntity()) }
}
