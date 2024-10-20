package com.barbzdev.f1elo.infrastructure

import com.barbzdev.f1elo.infrastructure.helper.DockerComposeHelper
import com.barbzdev.f1elo.infrastructure.testcases.HttpJolpiF1RepositoryShould
import org.junit.jupiter.api.Nested
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ApplicationIntegrationTest {

  companion object {
    @Container
    val dockerContainer = DockerComposeHelper.create()
  }

  @Nested
  inner class F1Repository : HttpJolpiF1RepositoryShould()
}
