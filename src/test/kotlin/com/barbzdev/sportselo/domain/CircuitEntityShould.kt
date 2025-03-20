package com.barbzdev.sportselo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CircuitEntityShould {
  @Test
  fun `create a circuit successfully`() {
    val aCircuit =
      Circuit.create(
        id = "albert_park",
        name = "Albert Park",
        latitude = "-37.8497",
        longitude = "144.968",
        country = "Australia",
        locality = "Melbourne",
        infoUrl = "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit")

    assertThat(aCircuit)
      .isEqualTo(
        Circuit.create(
          id = "albert_park",
          name = "Albert Park",
          latitude = "-37.8497",
          longitude = "144.968",
          country = "Australia",
          locality = "Melbourne",
          infoUrl = "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"))
  }
}
