package com.barbzdev.sportselo.domain.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class NationalityShould {

  @ParameterizedTest(name = "{index} => gentilic={0}, expectedCode={1}, expectedCountry={2}")
  @CsvSource(
    "Dutch, NL, Netherlands",
    "Austrian, AT, Austria",
    "Mexican, MX, Mexico",
    "Italian, IT, Italy",
    "Spanish, ES, Spain",
    "Monegasque, MC, Monaco",
    "German, DE, Germany",
    "British, GB, United Kingdom",
    "Australian, AU, Australia",
    "Canadian, CA, Canada",
    "Swiss, CH, Switzerland",
    "Chinese, CN, China",
    "Danish, DK, Denmark",
    "American, US, United States",
    "Japanese, JP, Japan",
    "Thai, TH, Thailand",
    "French, FR, France",
    "Finnish, FI, Finland",
    "Swedish, SE, Sweden",
    "Argentine, AR, Argentina",
    "Brazilian, BR, Brazil",
    "South African, ZA, South Africa",
    "Hong Kong, HK, Hong Kong",
    "Uruguayan, UY, Uruguay",
    "Colombian, CO, Colombia",
    "Portuguese, PT, Portugal",
    "Russian, RU, Russia",
    "Irish, IE, Ireland",
    "Belgian, BE, Belgium",
    "not supported nationality, UNKNOWN, Unknown")
  fun `return correct Nationality for given gentilic`(gentilic: String, expectedCode: String, expectedCountry: String) {
    val nationality = Nationality.fromGentilic(gentilic)

    assertThat(nationality.countryCode).isEqualTo(expectedCode)
    assertThat(nationality.countryName).isEqualTo(expectedCountry)
  }

  @ParameterizedTest(name = "{index} => expectedGentilic={0}, code={1}, expectedCountry={2}")
  @CsvSource(
    "Dutch, NL, Netherlands",
    "Austrian, AT, Austria",
    "Mexican, MX, Mexico",
    "Italian, IT, Italy",
    "Spanish, ES, Spain",
    "Monegasque, MC, Monaco",
    "German, DE, Germany",
    "British, GB, United Kingdom",
    "Australian, AU, Australia",
    "Canadian, CA, Canada",
    "Swiss, CH, Switzerland",
    "Chinese, CN, China",
    "Danish, DK, Denmark",
    "American, US, United States",
    "Japanese, JP, Japan",
    "Thai, TH, Thailand",
    "French, FR, France",
    "Finnish, FI, Finland",
    "Swedish, SE, Sweden",
    "Argentine, AR, Argentina",
    "Brazilian, BR, Brazil",
    "South_African, ZA, South Africa",
    "Hong_Kong, HK, Hong Kong",
    "Uruguayan, UY, Uruguay",
    "Colombian, CO, Colombia",
    "Portuguese, PT, Portugal",
    "Russian, RU, Russia",
    "Irish, IE, Ireland",
    "Belgian, BE, Belgium",
    "UNKNOWN, UNKNOWN, Unknown")
  fun `return correct Nationality for given country code`(
    expectedGentilic: String,
    code: String,
    expectedCountry: String
  ) {
    val nationality = Nationality.fromCountryCode(code)

    assertThat(nationality.name).isEqualTo(expectedGentilic, ignoreCase = true)
    assertThat(nationality.countryName).isEqualTo(expectedCountry)
  }
}
