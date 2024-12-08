package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.factory.ConstructorFactory.aConstructor
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.jpa.JpaConstructorRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class JpaConstructorRepositoryShould : IntegrationTestConfiguration() {
  @Autowired private lateinit var repository: JpaConstructorRepository
  @Autowired private lateinit var datasource: JpaConstructorDatasource

  @Test
  fun `find a constructor by id`() {
    val constructorInDatabase = givenAConstructorInDatabase()

    val constructor = repository.findBy(constructorInDatabase.id())

    assertThat(constructor).isNotNull
    assertThat(constructor).isEqualTo(constructorInDatabase)
  }

  private fun givenAConstructorInDatabase() = aConstructor().also { datasource.save(it.toEntity()) }
}
