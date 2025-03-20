package com.barbzdev.sportselo.factory

import com.barbzdev.sportselo.formulaone.domain.Driver.Companion.create
import com.barbzdev.sportselo.formulaone.domain.Driver.Companion.createRookie

object DriverFactory {
  private val anEloRecord =
    mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)

  private val anIRatingRecord =
    mapOf("2023-03-05" to 2000, "2023-03-19" to 1900, "2023-03-26" to 2300, "2023-04-30" to 2100)

  private val simpleEloRecord = mapOf("1900-01-01" to 2000)

  private val drivers =
    listOf(
      create(
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
        nationality = "Spanish",
        infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        currentElo = 2100,
        currentEloOccurredOn = "2023-04-30",
        eloRecord = anEloRecord,
        currentIRating = 2000,
        currentIRatingOccurredOn = "2023-04-30",
        iRatingRecord = anIRatingRecord,
      ),
      create(
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
      ),
      create(
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
      nationality = "Spanish",
      infoUrl = "https://en.wikipedia.org/wiki/Fernando_Alonso",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = simpleEloRecord,
      currentIRating = 2000,
      currentIRatingOccurredOn = "2023-04-30",
      iRatingRecord = anIRatingRecord,
    )

  val verstappen =
    create(
      id = "max_verstappen",
      givenName = "Max",
      familyName = "Verstappen",
      code = "VER",
      permanentNumber = "33",
      birthDate = "1997-09-30",
      nationality = "Dutch",
      infoUrl = "http://en.wikipedia.org/wiki/Max_Verstappen",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = simpleEloRecord,
      currentIRating = 2000,
      currentIRatingOccurredOn = "2023-04-30",
      iRatingRecord = anIRatingRecord,
    )

  val raikkonen =
    create(
      id = "raikkonen",
      givenName = "Kimi",
      familyName = "Räikkönen",
      code = "RAI",
      permanentNumber = "7",
      birthDate = "1979-10-17",
      nationality = "Finnish",
      infoUrl = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = anEloRecord,
      currentIRating = 2000,
      currentIRatingOccurredOn = "2023-04-30",
      iRatingRecord = anIRatingRecord,
    )

  val hamilton =
    create(
      id = "hamilton",
      givenName = "Lewis",
      familyName = "Hamilton",
      code = "HAM",
      permanentNumber = "44",
      birthDate = "1985-01-07",
      nationality = "British",
      infoUrl = "https://en.wikipedia.org/wiki/Lewis_Hamilton",
      currentElo = 2000,
      currentEloOccurredOn = "2023-04-30",
      eloRecord = simpleEloRecord,
      currentIRating = 2000,
      currentIRatingOccurredOn = "2023-04-30",
      iRatingRecord = anIRatingRecord,
    )
}
