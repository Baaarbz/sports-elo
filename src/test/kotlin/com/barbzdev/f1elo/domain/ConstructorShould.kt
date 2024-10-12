package com.barbzdev.f1elo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ConstructorShould {
  @Test
  fun `create a constructor successfully`() {
    val aConstructor = Constructor.create(
      "mercedes", "Mercedes", "German", "https://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One"
    )

    assertThat(aConstructor).isEqualTo(
      Constructor.create(
        "mercedes", "Mercedes", "German", "https://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One"
      )
    )
  }
}
