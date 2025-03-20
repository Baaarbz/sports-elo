package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.factory.ConstructorFactory.aConstructor
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.sportselo.infrastructure.jpa.JpaConstructorRepository
import com.barbzdev.sportselo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
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
