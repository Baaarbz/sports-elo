package com.barbzdev.f1elo.domain

import java.time.LocalDate


class Driver private constructor(
  private val id: DriverId,
  private val name: DriverName,
  private val currentElo: Elo,
  private val eloRecord: Set<Elo>
) {

  fun id() = id
  fun name() = name
  fun currentElo() = currentElo
  fun eloRecord() = eloRecord

  fun highestElo(): Elo? = eloRecord.maxByOrNull { it.value }

  fun lowestElo(): Elo? = eloRecord.minByOrNull { it.value }

  fun updateElo(newElo: Elo): Driver = create(id, name, newElo, eloRecord.plus(newElo))

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Driver

    if (id != other.id) return false
    if (name != other.name) return false
    if (currentElo != other.currentElo) return false
    if (eloRecord != other.eloRecord) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + currentElo.hashCode()
    result = 31 * result + eloRecord.hashCode()
    return result
  }

  override fun toString(): String = "Driver(id=$id, name=$name, currentElo=$currentElo, eloRecord=$eloRecord)"

  companion object {
    fun create(id: DriverId, name: DriverName, currentElo: Elo, eloRecord: Set<Elo>) = Driver(
      id = id,
      name = name,
      currentElo = currentElo,
      eloRecord = eloRecord
    )
  }
}

data class DriverId(val value: String)
data class DriverName(val value: String)
data class Elo(val value: Int, val occurredOn: LocalDate)
