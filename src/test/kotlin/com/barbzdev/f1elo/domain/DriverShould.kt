package com.barbzdev.f1elo.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import java.time.LocalDate.now
import org.junit.jupiter.api.Test


class DriverShould {

  @Test
  fun `get highest elo record of the driver`() {
    val anEloRecord = setOf(
      Elo(2000, now()),
      Elo(1900, now()),
      Elo(2300, now()),
      Elo(2100, now())
    )
    val aDriver = Driver.create(DriverId("any-id"), DriverName("Fernando Alonso"), Elo(2100, now()), anEloRecord)

    val highestElo = aDriver.highestElo()

    assertThat(highestElo).isEqualTo(Elo(2300, now()))
  }

  @Test
  fun `get lowest elo record of the driver`() {
    val anEloRecord = setOf(
      Elo(2000, now()),
      Elo(1900, now()),
      Elo(2300, now()),
      Elo(2100, now())
    )
    val aDriver = Driver.create(DriverId("any-id"), DriverName("Max Verstappen"), Elo(2100, now()), anEloRecord)

    val lowestElo = aDriver.lowestElo()

    assertThat(lowestElo).isEqualTo(Elo(1900, now()))
  }
}
