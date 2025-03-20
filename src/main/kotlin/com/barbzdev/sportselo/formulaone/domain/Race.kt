package com.barbzdev.sportselo.formulaone.domain

import com.barbzdev.sportselo.formulaone.domain.valueobject.InfoUrl
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceDate
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceId
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceName
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResult
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResultStatus
import com.barbzdev.sportselo.formulaone.domain.valueobject.race.Round
import java.util.UUID

class Race
private constructor(
  private val id: RaceId,
  private val round: Round,
  private val infoUrl: InfoUrl,
  private val name: RaceName,
  private val circuit: Circuit,
  private val occurredOn: RaceDate,
  private val results: List<RaceResult>
) {
  fun id() = id

  fun round() = round

  fun infoUrl() = infoUrl

  fun name() = name

  fun circuit() = circuit

  fun occurredOn() = occurredOn

  fun results() = results

  fun addResult(
    number: String,
    driver: Driver,
    position: Int,
    points: Float,
    constructor: Constructor,
    grid: Int,
    laps: Int,
    status: String,
    timeInMillis: Long?,
    fastestLapInMillis: Long?,
    averageSpeed: Float?,
    averageSpeedUnit: String?
  ): Race {
    val result =
      RaceResult(
        id = UUID.randomUUID().toString(),
        number = number,
        driver = driver,
        position = position,
        points = points,
        constructor = constructor,
        grid = grid,
        laps = laps,
        status = RaceResultStatus.fromText(status),
        timeInMillis = timeInMillis,
        fastestLapInMillis = fastestLapInMillis,
        averageSpeed = averageSpeed,
        averageSpeedUnit = averageSpeedUnit
      )
    return Race(id, round, infoUrl, name, circuit, occurredOn, results.plus(result))
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Race

    if (id != other.id) return false
    if (round != other.round) return false
    if (infoUrl != other.infoUrl) return false
    if (name != other.name) return false
    if (circuit != other.circuit) return false
    if (occurredOn != other.occurredOn) return false
    if (results != other.results) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + round.hashCode()
    result = 31 * result + infoUrl.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + circuit.hashCode()
    result = 31 * result + occurredOn.hashCode()
    result = 31 * result + results.hashCode()
    return result
  }

  override fun toString(): String {
    return "Race(id=$id, round=$round, infoUrl=$infoUrl, name=$name, circuit=$circuit, occurredOn=$occurredOn, results=$results)"
  }

  companion object {
    fun create(round: Int, infoUrl: String, name: String, circuit: Circuit, occurredOn: String) =
      Race(
        id = RaceId.generate(),
        round = Round(round),
        infoUrl = InfoUrl(infoUrl),
        name = RaceName(name),
        circuit = circuit,
        occurredOn = RaceDate.create(occurredOn),
        results = emptyList()
      )

    fun create(
      id: String,
      round: Int,
      infoUrl: String,
      name: String,
      circuit: Circuit,
      occurredOn: String,
      results: List<RaceResult>
    ) =
      Race(
        id = RaceId(id),
        round = Round(round),
        infoUrl = InfoUrl(infoUrl),
        name = RaceName(name),
        circuit = circuit,
        occurredOn = RaceDate.create(occurredOn),
        results = results
      )
  }
}

