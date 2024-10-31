package com.barbzdev.f1elo.infrastructure.testcases

import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.factory.SeasonFactory.aSeason
import com.barbzdev.f1elo.infrastructure.IntegrationTestConfiguration
import com.barbzdev.f1elo.infrastructure.spring.event.SpringSeasonLoadedDomainEvent
import com.barbzdev.f1elo.infrastructure.spring.event.listener.SpringDomainEventListener
import com.barbzdev.f1elo.infrastructure.spring.event.publisher.SpringSeasonDomainEventPublisher
import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


abstract class SpringSeasonDomainEventPublisherShould: IntegrationTestConfiguration() {

  @MockkBean(relaxed = true) private lateinit var springDomainEventListener: SpringDomainEventListener

  @Autowired private lateinit var springSeasonDomainEventPublisher: SpringSeasonDomainEventPublisher

  @Test
  fun `publish a season loaded event`() {
    val aSeasonLoaded = aSeason()

    springSeasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(aSeasonLoaded))

    verify { springDomainEventListener.on(SpringSeasonLoadedDomainEvent(aSeasonLoaded)) }
  }

}
