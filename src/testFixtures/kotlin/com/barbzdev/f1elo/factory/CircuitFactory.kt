package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.Circuit.Companion.create
import com.barbzdev.f1elo.domain.repository.F1Circuit
import com.barbzdev.f1elo.domain.repository.F1Location

object CircuitFactory {
  private val circuits = listOf(
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
      infoUrl = "https://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit"
    )
  )

  fun aCircuit() = circuits.random()

  private val f1Circuits = listOf(
    F1Circuit(
      circuitId = "monza",
      circuitName = "Autodromo Nazionale di Monza",
      url = "http://en.wikipedia.org/wiki/Autodromo_Nazionale_Monza",
      location = F1Location(
        country = "Italy",
        locality = "Monza",
        lat = "45.6156",
        long = "9.2811"
      )
    ),
    F1Circuit(
      circuitId = "interlagos",
      circuitName = "Autódromo José Carlos Pace",
      url = "http://en.wikipedia.org/wiki/Aut%C3%B3dromo_Jos%C3%A9_Carlos_Pace",
      location = F1Location(
        country = "Brazil",
        locality = "São Paulo",
        lat = "-23.7036",
        long = "-46.6997"
      )
    ),
    F1Circuit(
      circuitId = "silverstone",
      circuitName = "Silverstone Circuit",
      url = "http://en.wikipedia.org/wiki/Silverstone_Circuit",
      location = F1Location(
        country = "United Kingdom",
        locality = "Silverstone",
        lat = "52.0786",
        long = "-1.0169"
      )
    ),
    F1Circuit(
      circuitId = "spa",
      circuitName = "Circuit de Spa-Francorchamps",
      url = "http://en.wikipedia.org/wiki/Circuit_de_Spa-Francorchamps",
      location = F1Location(
        country = "Belgium",
        locality = "Spa",
        lat = "50.4372",
        long = "5.9714"
      )
    ),
  )

  fun aF1Circuit() = f1Circuits.random()
}
