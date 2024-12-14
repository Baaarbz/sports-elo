package com.barbzdev.f1elo.domain

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import com.barbzdev.f1elo.domain.common.Elo
import com.barbzdev.f1elo.domain.common.IRating
import org.junit.jupiter.api.Test

class DriverShould {

  @Test
  fun `create a driver successfully`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)

    val aDriver =
      Driver.create(
        id = "raikkonen",
        givenName = "Kimi",
        familyName = "Räikkönen",
        code = "RAI",
        permanentNumber = "7",
        birthDate = "1979-10-17",
        nationality = "Finnish",
        infoUrl = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
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
          nationality = "Finnish",
          infoUrl = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
          currentElo = 2100,
          currentEloOccurredOn = "2023-04-30",
          eloRecord = anEloRecord,
          currentIRating = 2000,
          currentIRatingOccurredOn = "2023-04-30",
          iRatingRecord = anIRatingRecord,
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
    assertThat(aDriver.currentElo()).isEqualTo(Elo(value = 1000, occurredOn = "1991-08-25"))
    assertThat(aDriver.eloRecord()).containsOnly(Elo(value = 1000, occurredOn = "1991-08-25"))
    assertThat(aDriver.currentIRating()).isEqualTo(IRating(value = 1000, occurredOn = "1991-08-25"))
    assertThat(aDriver.iRatingRecord()).containsOnly(IRating(value = 1000, occurredOn = "1991-08-25"))
  }

  @Test
  fun `get highest elo record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val aDriver =
      Driver.create(
        id = "alonso",
        givenName = "Fernando",
        familyName = "Alonso",
        code = "ALO",
        permanentNumber = "14",
        birthDate = "1981-07-29",
        nationality = "Spanish",
        infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val highestElo = aDriver.highestElo()

    assertThat(highestElo).isEqualTo(Elo(value = 2300, occurredOn = "2023-03-26"))
  }

  @Test
  fun `get lowest elo record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val aDriver =
      Driver.create(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        code = "VER",
        permanentNumber = "33",
        birthDate = "1997-09-30",
        nationality = "Dutch",
        infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val lowestElo = aDriver.lowestElo()

    assertThat(lowestElo).isEqualTo(Elo(value = 1900, occurredOn = "2023-03-19"))
  }

  @Test
  fun `update elo record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val outdatedDriver =
      Driver.create(
        id = "hamilton",
        givenName = "Lewis",
        familyName = "Hamilton",
        code = "HAM",
        permanentNumber = "44",
        birthDate = "1985-01-07",
        nationality = "British",
        infoUrl = "https://en.wikipedia.org/wiki/Lewis_Hamilton",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val updatedDriver = outdatedDriver.updateElo(value = 2400, occurredOn = "2023-05-07")

    assertThat(updatedDriver.currentElo())
      .isEqualToIgnoringGivenProperties(Elo(value = 2400, occurredOn = "2023-05-07"), Elo::occurredOn)
    assertThat(updatedDriver.eloRecord().last())
      .isEqualToIgnoringGivenProperties(Elo(value = 2400, occurredOn = "2023-05-07"), Elo::occurredOn)
  }

  @Test
  fun `reset elo record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val aDriver =
      Driver.create(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        code = "VER",
        permanentNumber = "33",
        birthDate = "1997-09-30",
        nationality = "Dutch",
        infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val driverWithEloReset = aDriver.resetElo()

    assertThat(driverWithEloReset.highestElo()).isEqualTo(Elo(value = 2000, occurredOn = "2023-03-05"))
    assertThat(driverWithEloReset.lowestElo()).isEqualTo(Elo(value = 2000, occurredOn = "2023-03-05"))
    assertThat(driverWithEloReset.currentElo()).isEqualTo(Elo(value = 2000, occurredOn = "2023-03-05"))
  }

  @Test
  fun `get highest iRating record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val aDriver =
      Driver.create(
        id = "alonso",
        givenName = "Fernando",
        familyName = "Alonso",
        code = "ALO",
        permanentNumber = "14",
        birthDate = "1981-07-29",
        nationality = "Spanish",
        infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val highestIRating = aDriver.highestIRating()

    assertThat(highestIRating).isEqualTo(IRating(value = 2300, occurredOn = "2023-03-26"))
  }

  @Test
  fun `get lowest iRating record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val aDriver =
      Driver.create(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        code = "VER",
        permanentNumber = "33",
        birthDate = "1997-09-30",
        nationality = "Dutch",
        infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val lowestIRating = aDriver.lowestIRating()

    assertThat(lowestIRating).isEqualTo(IRating(value = 1900, occurredOn = "2023-03-19"))
  }

  @Test
  fun `update iRating record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val outdatedDriver =
      Driver.create(
        id = "hamilton",
        givenName = "Lewis",
        familyName = "Hamilton",
        code = "HAM",
        permanentNumber = "44",
        birthDate = "1985-01-07",
        nationality = "British",
        infoUrl = "https://en.wikipedia.org/wiki/Lewis_Hamilton",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val updatedDriver = outdatedDriver.updateIRating(value = 2400, occurredOn = "2023-05-07")

    assertThat(updatedDriver.currentIRating())
      .isEqualToIgnoringGivenProperties(IRating(value = 2400, occurredOn = "2023-05-07"), IRating::occurredOn)
    assertThat(updatedDriver.iRatingRecord().last())
      .isEqualToIgnoringGivenProperties(IRating(value = 2400, occurredOn = "2023-05-07"), IRating::occurredOn)
  }

  @Test
  fun `reset iRating record of the driver`() {
    val anEloRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val anIRatingRecord = mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)
    val aDriver =
      Driver.create(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        code = "VER",
        permanentNumber = "33",
        birthDate = "1997-09-30",
        nationality = "Dutch",
        infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      )

    val driverWithIRatingReset = aDriver.resetIRating()

    assertThat(driverWithIRatingReset.highestIRating()).isEqualTo(IRating(value = 2000, occurredOn = "2023-03-05"))
    assertThat(driverWithIRatingReset.lowestIRating()).isEqualTo(IRating(value = 2000, occurredOn = "2023-03-05"))
    assertThat(driverWithIRatingReset.currentIRating()).isEqualTo(IRating(value = 2000, occurredOn = "2023-03-05"))
  }
}
