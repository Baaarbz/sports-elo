package com.barbzdev.sportselo.domain

import com.barbzdev.sportselo.domain.common.InfoUrl
import com.barbzdev.sportselo.domain.common.Nationality

class Constructor
private constructor(
  private val id: ConstructorId,
  private val name: ConstructorName,
  private val nationality: Nationality,
  private val infoUrl: InfoUrl
) {

  fun id() = id

  fun name() = name

  fun nationality() = nationality

  fun infoUrl() = infoUrl

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Constructor

    if (id != other.id) return false
    if (name != other.name) return false
    if (nationality != other.nationality) return false
    if (infoUrl != other.infoUrl) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + nationality.hashCode()
    result = 31 * result + infoUrl.hashCode()
    return result
  }

  override fun toString(): String = "Constructor(id=$id, name=$name, nationality=$nationality, infoUrl=$infoUrl)"

  companion object {
    fun create(id: String, name: String, nationality: String, infoUrl: String): Constructor =
      Constructor(ConstructorId(id), ConstructorName(name), Nationality.fromGentilic(nationality), InfoUrl(infoUrl))

    fun create(id: String, name: String, nationality: Nationality, infoUrl: String): Constructor =
      Constructor(ConstructorId(id), ConstructorName(name), nationality, InfoUrl(infoUrl))
  }
}

data class ConstructorId(val value: String) {
  init {
    require(value.isNotBlank()) { "ConstructorId cannot be blank" }
  }
}

data class ConstructorName(val value: String) {
  init {
    require(value.isNotBlank()) { "ConstructorName cannot be blank" }
  }
}
