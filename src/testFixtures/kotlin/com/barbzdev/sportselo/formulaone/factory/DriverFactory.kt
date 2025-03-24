package com.barbzdev.sportselo.formulaone.factory

import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn
import com.barbzdev.sportselo.formulaone.domain.Driver.Companion.create
import com.barbzdev.sportselo.formulaone.domain.Driver.Companion.createRookie
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.BRITISH
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.DUTCH
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.FINNISH
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality.SPANISH

object DriverFactory {
  private val AN_ELO_RECORD =
    listOf(
      Elo(2000, OccurredOn("2023-03-05")),
      Elo(1900, OccurredOn("2023-03-19")),
      Elo(2300, OccurredOn("2023-03-26")),
      Elo(2100, OccurredOn("2023-04-30")))

  private val SIMPLE_ELO_RECORD = listOf(Elo(2000, OccurredOn("1900-01-01")))

  private val drivers =
    listOf(
      create(
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
      ),
      createRookie(
        id = "michael_schumacher",
        givenName = "German",
        familyName = "Schumacher",
        code = "MSC",
        permanentNumber = null,
        birthDate = "1969-01-03",
        nationality = "German",
        infoUrl = "http://en.wikipedia.org/wiki/Michael_Schumacher",
        debutDate = "1991-08-25"),
      create(
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
        eloRecord = AN_ELO_RECORD),
      create(
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
        eloRecord = AN_ELO_RECORD),
      create(
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
      ))

  fun aDriver() = drivers.random()

  val alonso =
    create(
      id = "alonso",
      givenName = "Fernando",
      familyName = "Alonso",
      code = "ALO",
      permanentNumber = "14",
      birthDate = "1981-07-29",
      nationality = SPANISH,
      infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = SIMPLE_ELO_RECORD,
    )

  val verstappen =
    create(
      id = "max_verstappen",
      givenName = "Max",
      familyName = "Verstappen",
      code = "VER",
      permanentNumber = "33",
      birthDate = "1997-09-30",
      nationality = DUTCH,
      infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = SIMPLE_ELO_RECORD,
    )

  val raikkonen =
    create(
      id = "raikkonen",
      givenName = "Kimi",
      familyName = "Räikkönen",
      code = "RAI",
      permanentNumber = "7",
      birthDate = "1979-10-17",
      nationality = FINNISH,
      infoUrl = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = AN_ELO_RECORD,
    )

  val hamilton =
    create(
      id = "hamilton",
      givenName = "Lewis",
      familyName = "Hamilton",
      code = "HAM",
      permanentNumber = "44",
      birthDate = "1985-01-07",
      nationality = BRITISH,
      infoUrl = "https://en.wikipedia.org/wiki/Lewis_Hamilton",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = SIMPLE_ELO_RECORD,
    )
}
