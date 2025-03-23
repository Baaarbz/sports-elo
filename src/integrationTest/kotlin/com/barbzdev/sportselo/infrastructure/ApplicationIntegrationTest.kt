package com.barbzdev.sportselo.infrastructure

import com.barbzdev.sportselo.helper.DockerComposeHelper
import com.barbzdev.sportselo.infrastructure.testcases.HttpJolpiF1RepositoryShould
import com.barbzdev.sportselo.infrastructure.testcases.JpaDriverRepositoryShould
import com.barbzdev.sportselo.infrastructure.testcases.JpaSeasonRepositoryShould
import com.barbzdev.sportselo.infrastructure.testcases.EloReprocessingEventListenerShould
import com.barbzdev.sportselo.infrastructure.testcases.SpringSeasonDomainEventPublisherShould
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

  @Nested inner class EloReprocessingEventListener : EloReprocessingEventListenerShould()
}
