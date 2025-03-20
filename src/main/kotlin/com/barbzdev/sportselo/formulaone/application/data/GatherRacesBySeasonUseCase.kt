package com.barbzdev.sportselo.formulaone.application.data

import com.barbzdev.sportselo.formulaone.domain.Circuit
import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.domain.DriverId
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.domain.event.SeasonDomainEventPublisher
import com.barbzdev.sportselo.formulaone.domain.event.SeasonLoadedDomainEvent
import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.domain.repository.F1Circuit
import com.barbzdev.sportselo.formulaone.domain.repository.F1Constructor
import com.barbzdev.sportselo.formulaone.domain.repository.F1Driver
import com.barbzdev.sportselo.formulaone.domain.repository.F1Race
import com.barbzdev.sportselo.formulaone.domain.repository.F1Repository
import com.barbzdev.sportselo.formulaone.domain.repository.SeasonRepository

class GatherRacesBySeasonUseCase(
  private val f1Repository: F1Repository,
  private val seasonRepository: SeasonRepository,
  private val driverRepository: DriverRepository,
  private val seasonDomainEventPublisher: SeasonDomainEventPublisher,
  private val instrumentation: UseCaseInstrumentation
) {

  operator fun invoke(): GatherRacesBySeasonResponse = instrumentation {
    val seasonToLoad = getSeasonToLoad() ?: return@instrumentation GatherRacesOverASeasonNonExistent

    if (seasonToLoad.isCurrentSeason()) {
      return@instrumentation GatherRacesBySeasonUpToDate
    }

    seasonToLoad.loadF1RacesOfSeason().toDomain().saveSeasonLoaded(seasonToLoad).publishSeasonLoadedDomainEvent()

    GatherRacesBySeasonSuccess
  }

  private fun List<Race>.saveSeasonLoaded(seasonToLoad: Season): Season {
    val seasonToSave = seasonToLoad.addRacesOfSeason(this)
    seasonRepository.save(seasonToSave)
    return seasonToSave
  }

  private fun getSeasonToLoad(): Season? {
    val yearToLoad =
      seasonRepository.getLastYearLoaded()?.let { seasonFound -> seasonFound.value + 1 } ?: FIRST_FORMULA_1_SEASON

    return f1Repository.gatherAllSeasons().find { it.season == yearToLoad }?.let { Season.create(it.season, it.url) }
  }

  private fun Season.loadF1RacesOfSeason() = f1Repository.gatherRacesBySeason(this)

  private fun List<F1Race>.toDomain() = this.map { it.toRace() }

  private fun F1Race.toRace(): Race {
    var race =
      Race.create(
        round = this.round,
        infoUrl = this.url,
        name = this.raceName,
        circuit = this.circuit.toCircuit(),
        occurredOn = this.date)
    this.results.forEach { result ->
      race =
        race.addResult(
          number = result.number,
          driver = result.driver.toDriver(this.date),
          position = result.position.toInt(),
          points = result.points,
          constructor = result.constructor.toConstructor(),
          grid = result.grid,
          laps = result.laps,
          status = result.status,
          timeInMillis = result.time?.millis,
          fastestLapInMillis = result.fastestLap?.time?.millis,
          averageSpeed = result.fastestLap?.averageSpeed?.speed,
          averageSpeedUnit = result.fastestLap?.averageSpeed?.units)
    }
    return race
  }

  private fun F1Driver.toDriver(raceDate: String) =
    driverRepository.findBy(DriverId(driverId))
      ?: Driver.createRookie(
          id = driverId,
          givenName = givenName,
          familyName = familyName,
          birthDate = dateOfBirth,
          code = code,
          permanentNumber = permanentNumber,
          nationality = nationality,
          infoUrl = url,
          debutDate = raceDate)
        .saveIt()

  private fun Driver.saveIt() = driverRepository.save(this).let { this }

  private fun F1Circuit.toCircuit() =
    Circuit.create(
      id = this.circuitId,
      name = this.circuitName,
      infoUrl = this.url,
      latitude = this.location.lat,
      longitude = this.location.long,
      locality = this.location.locality,
      country = this.location.country)

  private fun F1Constructor.toConstructor() =
    Constructor.create(id = this.constructorId, name = this.name, nationality = this.nationality, infoUrl = this.url)

  private fun Season.publishSeasonLoadedDomainEvent() =
    seasonDomainEventPublisher.publish(SeasonLoadedDomainEvent(this))

  private companion object {
    const val FIRST_FORMULA_1_SEASON = 1950
  }
}

sealed class GatherRacesBySeasonResponse

data object GatherRacesBySeasonUpToDate : GatherRacesBySeasonResponse()

data object GatherRacesBySeasonSuccess : GatherRacesBySeasonResponse()

data object GatherRacesOverASeasonNonExistent : GatherRacesBySeasonResponse()
