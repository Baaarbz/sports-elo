package com.barbzdev.f1elo.domain

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import com.barbzdev.f1elo.domain.common.Elo
import org.junit.jupiter.api.Test


class DriverShould {

  @Test
  fun `create a driver successfully`() {
    val anEloRecord = mapOf(
      2000 to "2023-03-05",
      1900 to "2023-03-19",
      2300 to "2023-03-26",
      2100 to "2023-04-30"
    )

    val aDriver = Driver.create(
      "raikkonen", "Kimi", "Räikkönen", "RAI", "7", "1979-10-17", "Finnish",
      "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen", 2100, "2023-04-30", anEloRecord
    )

    assertThat(aDriver).isEqualTo(
      Driver.create(
        "raikkonen", "Kimi", "Räikkönen", "RAI", "7", "1979-10-17", "Finnish",
        "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen", 2100, "2023-04-30", anEloRecord
      )
    )
  }

  @Test
  fun `create a rookie successfully`() {
    val aDriver = Driver.createRookie(
      "michael_schumacher", "German", "Schumacher", "MSC", null, "1969-01-03", "German",
      "http://en.wikipedia.org/wiki/Michael_Schumacher", "1991-8-25"
    )

    assertThat(aDriver).isEqualTo(
      Driver.createRookie(
        "michael_schumacher", "German", "Schumacher", "MSC", null, "1969-01-03", "German",
        "http://en.wikipedia.org/wiki/Michael_Schumacher", "1991-8-25"
      )
    )
    assertThat(aDriver.currentElo()).isEqualTo(Elo(1000, "1991-8-25"))
    assertThat(aDriver.eloRecord()).containsOnly(Elo(1000, "1991-8-25"))
  }

  @Test
  fun `get highest elo record of the driver`() {
    val anEloRecord = mapOf(
      2000 to "2023-03-05",
      1900 to "2023-03-19",
      2300 to "2023-03-26",
      2100 to "2023-04-30"
    )
    val aDriver = Driver.create(
      "alonso", "Fernando", "Alonso", "ALO", "14", "1981-07-29", "Spanish",
      "https://en.wikipedia.org/wiki/Fernando_Alonso", 2100, "2023-04-30", anEloRecord
    )

    val highestElo = aDriver.highestElo()

    assertThat(highestElo).isEqualTo(Elo(2300, "2023-03-26"))
  }

  @Test
  fun `get lowest elo record of the driver`() {
    val anEloRecord = mapOf(
      2000 to "2023-03-05",
      1900 to "2023-03-19",
      2300 to "2023-03-26",
      2100 to "2023-04-30"
    )
    val aDriver = Driver.create(
      "max_verstappen", "Max", "Verstappen", "VER", "33", "1997-09-30", "Dutch",
      "http://en.wikipedia.org/wiki/Max_Verstappen", 2100, "2023-04-30", anEloRecord
    )

    val lowestElo = aDriver.lowestElo()

    assertThat(lowestElo).isEqualTo(Elo(1900, "2023-03-19"))
  }

  @Test
  fun `update elo record of the driver`() {
    val anEloRecord = mapOf(
      2000 to "2023-03-05",
      1900 to "2023-03-19",
      2300 to "2023-03-26",
      2100 to "2023-04-30"
    )
    val outdatedDriver = Driver.create(
      "hamilton", "Lewis", "Hamilton", "HAM", "44", "1985-01-07", "British",
      "https://en.wikipedia.org/wiki/Lewis_Hamilton", 2100, "2023-04-30", anEloRecord
    )

    val updatedDriver = outdatedDriver.updateElo(2400, "2023-05-07")

    assertThat(updatedDriver.currentElo()).isEqualToIgnoringGivenProperties(Elo(2400, "2023-05-07"), Elo::occurredOn)
    assertThat(updatedDriver.eloRecord().last()).isEqualToIgnoringGivenProperties(Elo(2400, "2023-05-07"), Elo::occurredOn)
  }
}
