package com.barbzdev.f1elo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RaceShould {
  @Test
  fun `create a race successfully`() {
    val aCircuit = Circuit.create(
      "albert_park", "Albert Park", "-37.8497", "144.968", "Australia",
      "Melbourne", "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"
    )
    val aRace = Race.create(
      2010, 1,
      "https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix",
      "Australian Grand Prix", aCircuit, "2010-03-28", emptyList()
    )

    assertThat(aRace).isEqualTo(
      Race.create(
        2010, 1,
        "https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix",
        "Australian Grand Prix", aCircuit, "2010-03-28", emptyList()
      )
    )
  }
}
