package com.barbzdev.f1elo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CircuitEntityShould {
  @Test
  fun `create a circuit successfully`() {
    val aCircuit = Circuit.create(
      "albert_park", "Albert Park", "-37.8497", "144.968", "Australia",
      "Melbourne", "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"
    )

    assertThat(aCircuit).isEqualTo(
      Circuit.create(
        "albert_park", "Albert Park", "-37.8497", "144.968", "Australia",
        "Melbourne", "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"
      )
    )
  }
}
