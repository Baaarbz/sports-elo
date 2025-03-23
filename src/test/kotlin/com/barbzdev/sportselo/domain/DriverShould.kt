package com.barbzdev.sportselo.domain

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.BRITISH
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.DUTCH
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.FINNISH
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.SPANISH
import org.junit.jupiter.api.Test

class DriverShould {

  @Test
  fun `create a driver successfully`() {
    val aDriver =
      Driver.create(
        id = "raikkonen",
        givenName = "Kimi",
        familyName = "Räikkönen",
        code = "RAI",
        permanentNumber = "7",
        birthDate = "1979-10-17",
        nationality = FINNISH,
        infoUrl = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = AN_ELO_RECORD,
      )

    assertThat(aDriver)
      .isEqualTo(
        Driver.create(
          id = "raikkonen",
          givenName = "Kimi",
          familyName = "Räikkönen",
          code = "RAI",
          permanentNumber = "7",
          birthDate = "1979-10-17",
          nationality = FINNISH,
          infoUrl = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
          currentElo = 2100,
          currentEloOccurredOn = "2023-04-30",
          eloRecord = AN_ELO_RECORD,
        ))
  }

  @Test
  fun `create a rookie successfully`() {
    val aDriver =
      Driver.createRookie(
        id = "michael_schumacher",
        givenName = "German",
        familyName = "Schumacher",
        code = "MSC",
        permanentNumber = null,
        birthDate = "1969-01-03",
        nationality = "German",
        infoUrl = "http://en.wikipedia.org/wiki/Michael_Schumacher",
        debutDate = "1991-08-25")

    assertThat(aDriver)
      .isEqualTo(
        Driver.createRookie(
          id = "michael_schumacher",
          givenName = "German",
          familyName = "Schumacher",
          code = "MSC",
          permanentNumber = null,
          birthDate = "1969-01-03",
          nationality = "German",
          infoUrl = "http://en.wikipedia.org/wiki/Michael_Schumacher",
          debutDate = "1991-08-25"))
    assertThat(aDriver.currentElo()).isEqualTo(Elo(value = 1000, occurredOn = OccurredOn("1991-08-25")))
    assertThat(aDriver.eloRecord()).containsOnly(Elo(value = 1000, occurredOn = OccurredOn("1991-08-25")))
  }

  @Test
  fun `get highest elo record of the driver`() {
    val aDriver =
      Driver.create(
        id = "alonso",
        givenName = "Fernando",
        familyName = "Alonso",
        code = "ALO",
        permanentNumber = "14",
        birthDate = "1981-07-29",
        nationality = SPANISH,
        infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = AN_ELO_RECORD,
      )

    val highestElo = aDriver.highestElo()

    assertThat(highestElo).isEqualTo(Elo(value = 2300, occurredOn = OccurredOn("2023-03-26")))
  }

  @Test
  fun `get lowest elo record of the driver`() {
    val aDriver =
      Driver.create(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        code = "VER",
        permanentNumber = "33",
        birthDate = "1997-09-30",
        nationality = DUTCH,
        infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = AN_ELO_RECORD,
      )

    val lowestElo = aDriver.lowestElo()

    assertThat(lowestElo).isEqualTo(Elo(value = 1900, occurredOn = OccurredOn("2023-03-19")))
  }

  @Test
  fun `update elo record of the driver`() {
    val outdatedDriver =
      Driver.create(
        id = "hamilton",
        givenName = "Lewis",
        familyName = "Hamilton",
        code = "HAM",
        permanentNumber = "44",
        birthDate = "1985-01-07",
        nationality = BRITISH,
        infoUrl = "https://en.wikipedia.org/wiki/Lewis_Hamilton",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = AN_ELO_RECORD,
      )

    val updatedDriver = outdatedDriver.updateElo(value = 2400, occurredOn = "2023-05-07")

    assertThat(updatedDriver.currentElo()).isEqualTo(Elo(value = 2400, OccurredOn("2023-05-07")))
    assertThat(updatedDriver.eloRecord().last()).isEqualTo(Elo(value = 2400, occurredOn = OccurredOn("2023-05-07")))
  }

  @Test
  fun `reset elo record of the driver`() {
    val aDriver =
      Driver.create(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        code = "VER",
        permanentNumber = "33",
        birthDate = "1997-09-30",
        nationality = DUTCH,
        infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = AN_ELO_RECORD,
      )

    val driverWithEloReset = aDriver.resetElo()

    assertThat(driverWithEloReset.highestElo()).isEqualTo(Elo(value = 1000, occurredOn = OccurredOn("2023-03-05")))
    assertThat(driverWithEloReset.lowestElo()).isEqualTo(Elo(value = 1000, occurredOn = OccurredOn("2023-03-05")))
    assertThat(driverWithEloReset.currentElo()).isEqualTo(Elo(value = 1000, occurredOn = OccurredOn("2023-03-05")))
  }

  private companion object {
    val AN_ELO_RECORD =
      listOf(
        Elo(2000, OccurredOn("2023-03-05")),
        Elo(1900, OccurredOn("2023-03-19")),
        Elo(2300, OccurredOn("2023-03-26")),
        Elo(2100, OccurredOn("2023-04-30")))
  }
}
