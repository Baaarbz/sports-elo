package com.barbzdev.sportselo.formulaone.domain

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.barbzdev.sportselo.formulaone.domain.Circuit
import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResultStatus
import org.junit.jupiter.api.Test

class RaceShould {
  @Test
  fun `create a race successfully`() {
    val aCircuit =
      Circuit.create(
        id = "albert_park",
        name = "Albert Park",
        latitude = "-37.8497",
        longitude = "144.968",
        country = "Australia",
        locality = "Melbourne",
        infoUrl = "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit")
    val aRace =
      Race.create(
        round = 1,
        infoUrl = "https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix",
        name = "Australian Grand Prix",
        circuit = aCircuit,
        occurredOn = "2010-03-28")

    assertAll {
      assertThat(aRace.round().value).isEqualTo(1)
      assertThat(aRace.infoUrl().value).isEqualTo("https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix")
      assertThat(aRace.name().value).isEqualTo("Australian Grand Prix")
      assertThat(aRace.circuit()).isEqualTo(aCircuit)
      assertThat(aRace.occurredOn().date.value).isEqualTo("2010-03-28")
      assertThat(aRace.results()).isEmpty()
      assertThat(aRace.id()).isNotNull()
    }
  }

  @Test
  fun `add a result to a race`() {
    val aDriver =      Driver.createRookie(
        id = "michael_schumacher",
        givenName = "German",
        familyName = "Schumacher",
        code = "MSC",
        permanentNumber = null,
        birthDate = "1969-01-03",
        nationality = "German",
        infoUrl = "http://en.wikipedia.org/wiki/Michael_Schumacher",
        debutDate = "1991-08-25",)
    val aConstructor =      Constructor.create(
        id = "ferrari",
        name = "Ferrari",
        nationality = "Italian",
        infoUrl = "http://en.wikipedia.org/wiki/Scuderia_Ferrari",)
    val aCircuit =      Circuit.create(
        id = "albert_park",
        name = "Albert Park",
        latitude = "-37.8497",
        longitude = "144.968",
        country = "Australia",
        locality = "Melbourne",
        infoUrl = "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit",)
    val aRace =      Race.create(
        round = 1,
        infoUrl = "https://en.wikipedia.org/wiki/2023_Australian_Grand_Prix",
        name = "Australian Grand Prix",
        circuit = aCircuit,
        occurredOn = "2010-03-28",)

    val updatedRace =      aRace.addResult(
        number = "7",
        driver = aDriver,
        position = 1,
        points = 25f,
        constructor = aConstructor,
        grid = 1,
        laps = 58,
        status = "Finished",
        timeInMillis = 6000000,
        fastestLapInMillis = 60000,
        averageSpeed = 200f,
        averageSpeedUnit = "kph",)

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
