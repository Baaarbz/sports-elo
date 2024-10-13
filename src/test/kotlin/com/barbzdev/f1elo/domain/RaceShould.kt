package com.barbzdev.f1elo.domain

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
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
      "Australian Grand Prix", aCircuit, "2010-03-28"
    )

    assertAll {
      assertThat(aRace.season().value).isEqualTo(2010)
      assertThat(aRace.round().value).isEqualTo(1)
      assertThat(aRace.infoUrl().value).isEqualTo("https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix")
      assertThat(aRace.name().value).isEqualTo("Australian Grand Prix")
      assertThat(aRace.circuit()).isEqualTo(aCircuit)
      assertThat(aRace.occurredOn().value).isEqualTo("2010-03-28")
      assertThat(aRace.results()).isEmpty()
      assertThat(aRace.id()).isNotNull()
    }
  }

  @Test
  fun `add a result to a race`() {
    val aDriver = Driver.createRookie(
      "michael_schumacher", "German", "Schumacher", "MSC", null, "1969-01-03", "German",
      "http://en.wikipedia.org/wiki/Michael_Schumacher", "1991-8-25"
    )
    val aConstructor = Constructor.create(
      "ferrari", "Ferrari", "Italian", "http://en.wikipedia.org/wiki/Scuderia_Ferrari"
    )
    val aCircuit = Circuit.create(
      "albert_park", "Albert Park", "-37.8497", "144.968", "Australia",
      "Melbourne", "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"
    )
    val aRace = Race.create(
      2010, 1,
      "https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix",
      "Australian Grand Prix", aCircuit, "2010-03-28"
    )

    val updatedRace = aRace.addResult(
      "7", aDriver, 1, 25f, aConstructor, 1, 58, RaceResultStatus.FINISHED, 6000000, 60000, 200f, "kph"
    )

    assertThat(updatedRace.results().size).isEqualTo(1)
    assertThat(updatedRace.results().first().driver).isEqualTo(aDriver)
    assertThat(updatedRace.results().first().position).isEqualTo(1)
    assertThat(updatedRace.results().first().points).isEqualTo(25f)
    assertThat(updatedRace.results().first().constructor).isEqualTo(aConstructor)
    assertThat(updatedRace.results().first().grid).isEqualTo(1)
    assertThat(updatedRace.results().first().laps).isEqualTo(58)
    assertThat(updatedRace.results().first().status).isEqualTo(RaceResultStatus.FINISHED)
    assertThat(updatedRace.results().first().timeInMillis).isEqualTo(6000000)
    assertThat(updatedRace.results().first().fastestLapInMillis).isEqualTo(60000)
    assertThat(updatedRace.results().first().averageSpeed).isEqualTo(200f)
    assertThat(updatedRace.results().first().averageSpeedUnit).isEqualTo("kph")
    assertThat(updatedRace.results().first().id).isNotNull()
    assertThat(updatedRace.results().first().number).isEqualTo("7")
  }

}
