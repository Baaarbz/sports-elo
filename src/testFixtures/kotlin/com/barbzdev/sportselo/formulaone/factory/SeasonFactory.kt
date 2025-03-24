package com.barbzdev.sportselo.formulaone.factory

import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.repository.F1Season
import com.barbzdev.sportselo.formulaone.factory.RaceFactory.raceOf2014
import com.barbzdev.sportselo.formulaone.factory.RaceFactory.races

object SeasonFactory {
  private val seasons =
    listOf(
      Season.create(year = 1950, infoUrl = "http://en.wikipedia.org/wiki/1950_Formula_One_season")
        .addRacesOfSeason(races),
      Season.create(year = 2006, infoUrl = "http://en.wikipedia.org/wiki/2006_Formula_One_season")
        .addRacesOfSeason(races),
      Season.create(year = 2014, infoUrl = "http://en.wikipedia.org/wiki/2014_Formula_One_season")
        .addRacesOfSeason(races),
      Season.create(year = 2021, infoUrl = "https://en.wikipedia.org/wiki/2021_Formula_One_World_Championship")
        .addRacesOfSeason(races),
    )

  fun aSeason() = seasons.random()

  fun aSeason(year: Int) =
    Season.create(year = year, infoUrl = "https://en.wikipedia.org/wiki/${year}_Formula_One_World_Championship")
      .addRacesOfSeason(races)

  val seasonOf2014 =
    Season.create(year = 2014, infoUrl = "http://en.wikipedia.org/wiki/2014_Formula_One_season")
      .addRacesOfSeason(listOf(raceOf2014))

  val f1Seasons =
    listOf(
      F1Season(season = 1950, url = "http://en.wikipedia.org/wiki/1950_Formula_One_season"),
      F1Season(season = 1951, url = "http://en.wikipedia.org/wiki/1951_Formula_One_season"),
      F1Season(season = 2006, url = "http://en.wikipedia.org/wiki/2006_Formula_One_season"),
      F1Season(season = 2007, url = "http://en.wikipedia.org/wiki/2007_Formula_One_season"),
      F1Season(season = 2014, url = "http://en.wikipedia.org/wiki/2014_Formula_One_season"),
      F1Season(season = 2015, url = "http://en.wikipedia.org/wiki/2015_Formula_One_season"),
      F1Season(season = 2021, url = "https://en.wikipedia.org/wiki/2021_Formula_One_World_Championship"),
      F1Season(season = 2022, url = "https://en.wikipedia.org/wiki/2022_Formula_One_World_Championship"),
    )

  fun aF1Season(year: Int) =
    F1Season(season = year, url = "https://en.wikipedia.org/wiki/${year}_Formula_One_World_Championship")
}
