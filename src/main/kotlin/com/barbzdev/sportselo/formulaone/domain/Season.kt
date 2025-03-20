package com.barbzdev.sportselo.formulaone.domain

import com.barbzdev.sportselo.formulaone.domain.valueobject.InfoUrl
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonId
import com.barbzdev.sportselo.formulaone.domain.valueobject.season.SeasonYear
import java.time.LocalDate.now

class Season
private constructor(
  private val id: SeasonId,
  private val year: SeasonYear,
  private val infoUrl: InfoUrl,
  private val races: List<Race>

) {
  fun id() = id

  fun year() = year

  fun infoUrl() = infoUrl

  fun races() = races

  fun isCurrentSeason() = year.value >= now().year

  fun addRacesOfSeason(races: List<Race>): Season {
    val filteredRaces = races.filter { it.name().value != "Indianapolis 500" }
    return Season(id = id, year = year, infoUrl = infoUrl, races = this.races.plus(filteredRaces))
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Season

    if (id != other.id) return false
    if (year != other.year) return false
    if (infoUrl != other.infoUrl) return false
    if (races != other.races) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + year.hashCode()
    result = 31 * result + infoUrl.hashCode()
    result = 31 * result + races.hashCode()
    return result
  }

  override fun toString(): String {
    return "Season(id=$id, year=$year, infoUrl=$infoUrl, races=$races)"
  }

  companion object {
    fun create(
      year: Int,
      infoUrl: String,
    ): Season =
      Season(id = SeasonId.generate(), year = SeasonYear(year), infoUrl = InfoUrl(infoUrl), races = emptyList())

    fun create(
      id: String,
      year: Int,
      infoUrl: String,
    ): Season = Season(id = SeasonId(id), year = SeasonYear(year), infoUrl = InfoUrl(infoUrl), races = emptyList())
  }
}
