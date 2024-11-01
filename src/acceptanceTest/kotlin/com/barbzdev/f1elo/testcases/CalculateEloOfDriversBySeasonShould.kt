package com.barbzdev.f1elo.testcases

import com.barbzdev.f1elo.domain.DriverId
import com.barbzdev.f1elo.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.f1elo.factory.SeasonFactory.seasonOf2014
import com.barbzdev.f1elo.infrastructure.com.barbzdev.f1elo.AcceptanceTestConfiguration
import com.barbzdev.f1elo.infrastructure.spring.event.publisher.SpringSeasonDomainEventPublisher
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

abstract class CalculateEloOfDriversBySeasonShould : AcceptanceTestConfiguration() {
  @Autowired private lateinit var springSeasonDomainEventPublisher: SpringSeasonDomainEventPublisher

  @Test
  fun `calculate elo of drivers by season`() {
    givenASeasonInDatabase()

    whenSeasonLoadedEventPublished()

    thenVerifyEloOfDriversCalculated()
  }

  private fun givenASeasonInDatabase() = seasonRepository.save(SEASON_IN_DATABASE)

  private fun whenSeasonLoadedEventPublished() =
    springSeasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(SEASON_IN_DATABASE))

  private fun thenVerifyEloOfDriversCalculated() {
    await().untilAsserted {
      val alonso = driverRepository.findBy(ALONSO_ID)!!
      val hamilton = driverRepository.findBy(HAMILTON_ID)!!

      assertThat(alonso.currentElo().rating).isEqualTo(2016)
      assertThat(hamilton.currentElo().rating).isEqualTo(1984)
    }
  }

  private companion object {
    val ALONSO_ID = DriverId("alonso")
    val HAMILTON_ID = DriverId("hamilton")
    val SEASON_IN_DATABASE = seasonOf2014
  }
}
