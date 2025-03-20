package com.barbzdev.sportselo.testcases

import com.barbzdev.sportselo.AcceptanceTestConfiguration
import com.barbzdev.sportselo.domain.DriverId
import com.barbzdev.sportselo.formulaone.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.sportselo.factory.SeasonFactory.seasonOf2014
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.SpringSeasonDomainEventPublisher
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

      assertThat(alonso.currentElo().value).isEqualTo(2016)
      assertThat(hamilton.currentElo().value).isEqualTo(1984)
    }
  }

  private companion object {
    val ALONSO_ID = DriverId("alonso")
    val HAMILTON_ID = DriverId("hamilton")
    val SEASON_IN_DATABASE = seasonOf2014
  }
}
