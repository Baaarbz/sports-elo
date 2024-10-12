package com.barbzdev.f1elo.domain

import com.barbzdev.f1elo.domain.common.InfoUrl

class Circuit private constructor(
  private val id: CircuitId,
  private val name: CircuitName,
  private val location: CircuitLocation,
  private val country: CircuitCountry,
  private val locality: CircuitLocality,
  private val infoUrl: InfoUrl
) {

  fun id() = id
  fun name() = name
  fun location() = location
  fun country() = country
  fun locality() = locality
  fun infoUrl() = infoUrl

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Circuit

    if (id != other.id) return false
    if (name != other.name) return false
    if (location != other.location) return false
    if (country != other.country) return false
    if (locality != other.locality) return false
    if (infoUrl != other.infoUrl) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + location.hashCode()
    result = 31 * result + country.hashCode()
    result = 31 * result + locality.hashCode()
    result = 31 * result + infoUrl.hashCode()
    return result
  }

  override fun toString(): String =
    "Circuit(id=$id, name=$name, location=$location, country=$country, locality=$locality, infoUrl=$infoUrl)"

  companion object {
    fun create(
      id: String,
      name: String,
      latitude: String,
      longitude: String,
      country: String,
      locality: String,
      infoUrl: String
    ): Circuit = Circuit(
      CircuitId(id),
      CircuitName(name),
      CircuitLocation(latitude, longitude),
      CircuitCountry(country),
      CircuitLocality(locality),
      InfoUrl(infoUrl)
    )
  }
}

data class CircuitId(val value: String) {
  init {
    require(value.isNotBlank())
  }
}

data class CircuitName(val value: String) {
  init {
    require(value.isNotBlank())
  }
}

data class CircuitLocation(val latitude: String, val longitude: String) {
  init {
    require(latitude.isNotBlank())
    require(longitude.isNotBlank())
  }
}

data class CircuitCountry(val value: String) {
  init {
    require(value.isNotBlank())
  }
}

data class CircuitLocality(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
