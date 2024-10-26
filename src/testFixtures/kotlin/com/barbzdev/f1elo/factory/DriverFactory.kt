package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.Driver.Companion.create
import com.barbzdev.f1elo.domain.Driver.Companion.createRookie
import com.barbzdev.f1elo.domain.repository.F1Driver

object DriverFactory {
  private val anEloRecord =
    mapOf(2000 to "2023-03-05", 1900 to "2023-03-19", 2300 to "2023-03-26", 2100 to "2023-04-30")
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
        eloRecord = anEloRecord),
      createRookie(
        id = "michael_schumacher",
        givenName = "German",
        familyName = "Schumacher",
        code = "MSC",
        permanentNumber = null,
        birthDate = "1969-01-03",
        nationality = "German",
        infoUrl = "http://en.wikipedia.org/wiki/Michael_Schumacher",
        debutDate = "1991-8-25"),
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
        eloRecord = anEloRecord),
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
        eloRecord = anEloRecord),
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
        eloRecord = anEloRecord))

  fun aDriver() = drivers.random()

  private val f1Drivers =
    listOf(
      F1Driver(
        driverId = "raikkonen",
        givenName = "Kimi",
        familyName = "Räikkönen",
        dateOfBirth = "1979-10-17",
        code = "RAI",
        permanentNumber = "7",
        nationality = "Finnish",
        url = "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen"),
      F1Driver(
        driverId = "michael_schumacher",
        givenName = "German",
        familyName = "Schumacher",
        dateOfBirth = "1969-01-03",
        code = "MSC",
        url = "http://en.wikipedia.org/wiki/Michael_Schumacher",
        nationality = "German",
        permanentNumber = null,
      ),
      F1Driver(
        driverId = "alonso",
        givenName = "Fernando",
        familyName = "Alonso",
        dateOfBirth = "1981-07-29",
        code = "ALO",
        permanentNumber = "14",
        url = "https://en.wikipedia.org/wiki/Fernando_Alonso",
        nationality = "Spanish"))

  fun aF1Driver() = f1Drivers.random()
}
