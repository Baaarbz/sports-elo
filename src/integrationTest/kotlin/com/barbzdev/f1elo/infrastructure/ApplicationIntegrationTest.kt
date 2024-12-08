package com.barbzdev.f1elo.infrastructure

import com.barbzdev.f1elo.helper.DockerComposeHelper
import com.barbzdev.f1elo.infrastructure.testcases.HttpJolpiF1RepositoryShould
import com.barbzdev.f1elo.infrastructure.testcases.JpaConstructorRepositoryShould
import com.barbzdev.f1elo.infrastructure.testcases.JpaDriverRepositoryShould
import com.barbzdev.f1elo.infrastructure.testcases.JpaSeasonRepositoryShould
import com.barbzdev.f1elo.infrastructure.testcases.JpaTheoreticalPerformanceRepositoryShould
import com.barbzdev.f1elo.infrastructure.testcases.SpringSeasonDomainEventPublisherShould
import org.junit.jupiter.api.Nested
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ApplicationIntegrationTest {

  companion object {
    @Container val dockerContainer = DockerComposeHelper.create()
  }

  @Nested inner class HttpJolpiF1Repository : HttpJolpiF1RepositoryShould()

  @Nested inner class JpaSeasonRepository : JpaSeasonRepositoryShould()

  @Nested inner class JpaDriverRepository : JpaDriverRepositoryShould()

  @Nested inner class SpringSeasonDomainEventPublisher : SpringSeasonDomainEventPublisherShould()

  @Nested inner class JpaTheoreticalPerformanceRepository : JpaTheoreticalPerformanceRepositoryShould()

  @Nested inner class JpaConstructorRepository : JpaConstructorRepositoryShould()
}
