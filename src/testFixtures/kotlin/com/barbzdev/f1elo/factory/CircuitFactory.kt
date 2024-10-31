package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.Circuit.Companion.create

object CircuitFactory {
  private val circuits =
    listOf(
      create(
        id = "monza",
        name = "Autodromo Nazionale di Monza",
        infoUrl = "http://en.wikipedia.org/wiki/Autodromo_Nazionale_Monza",
        country = "Italy",
        locality = "Monza",
        latitude = "45.6156",
        longitude = "9.2811",
      ),
      create(
        id = "interlagos",
        name = "Autódromo José Carlos Pace",
        infoUrl = "http://en.wikipedia.org/wiki/Aut%C3%B3dromo_Jos%C3%A9_Carlos_Pace",
        country = "Brazil",
        locality = "São Paulo",
        latitude = "-23.7036",
        longitude = "-46.6997",
      ),
      create(
        id = "silverstone",
        name = "Silverstone Circuit",
        infoUrl = "http://en.wikipedia.org/wiki/Silverstone_Circuit",
        country = "United Kingdom",
        locality = "Silverstone",
        latitude = "52.0786",
        longitude = "-1.0169",
      ),
      create(
        id = "spa",
        name = "Circuit de Spa-Francorchamps",
        infoUrl = "http://en.wikipedia.org/wiki/Circuit_de_Spa-Francorchamps",
        country = "Belgium",
        locality = "Spa",
        latitude = "50.4372",
        longitude = "5.9714",
      ),
      create(
        id = "monaco",
        name = "Circuit de Monaco",
        infoUrl = "http://en.wikipedia.org/wiki/Circuit_de_Monaco",
        country = "Monaco",
        locality = "Monte Carlo",
        latitude = "43.7347",
        longitude = "7.4206",
      ),
      create(
        id = "hungaroring",
        name = "Hungaroring",
        infoUrl = "http://en.wikipedia.org/wiki/Hungaroring",
        country = "Hungary",
        locality = "Budapest",
        latitude = "47.5789",
        longitude = "19.2486",
      ),
      create(
        id = "albert_park",
        name = "Albert Park",
        latitude = "-37.8497",
        longitude = "144.968",
        country = "Australia",
        locality = "Melbourne",
        infoUrl = "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"))

  fun aCircuit() = circuits.random()

  val interlagos =
    create(
      id = "interlagos",
      name = "Autódromo José Carlos Pace",
      infoUrl = "http://en.wikipedia.org/wiki/Aut%C3%B3dromo_Jos%C3%A9_Carlos_Pace",
      country = "Brazil",
      locality = "São Paulo",
      latitude = "-23.7036",
      longitude = "-46.6997",
    )
}
