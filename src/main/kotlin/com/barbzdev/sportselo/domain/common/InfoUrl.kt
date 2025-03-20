package com.barbzdev.sportselo.domain.common

data class InfoUrl(val value: String) {
  init {
    require(value.isNotBlank()) { "InfoUrl cannot be blank" }
    require(isValidUrl(value)) { "Invalid URL format" }
  }

  private fun isValidUrl(url: String): Boolean = urlRegex.matches(url)

  private companion object {
    val urlRegex = "^(https?)://[^\\s/$.?#].\\S*$".toRegex()
  }
}
