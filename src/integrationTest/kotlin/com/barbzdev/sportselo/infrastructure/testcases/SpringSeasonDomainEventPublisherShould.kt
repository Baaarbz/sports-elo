package com.barbzdev.sportselo.infrastructure.testcases

import com.barbzdev.sportselo.formulaone.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.sportselo.factory.SeasonFactory.aSeason
import com.barbzdev.sportselo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.SpringSeasonLoadedDomainEvent
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.SpringDomainEventListener
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.SpringSeasonDomainEventPublisher
import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class SpringSeasonDomainEventPublisherShould : IntegrationTestConfiguration() {

  @MockkBean(relaxed = true) private lateinit var springDomainEventListener: SpringDomainEventListener

  @Autowired private lateinit var springSeasonDomainEventPublisher: SpringSeasonDomainEventPublisher

  @Test
  fun `publish a season loaded event`() {
    val aSeasonLoaded = aSeason()

    springSeasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(aSeasonLoaded))

    verify { springDomainEventListener.on(SpringSeasonLoadedDomainEvent(aSeasonLoaded)) }
  }
}
