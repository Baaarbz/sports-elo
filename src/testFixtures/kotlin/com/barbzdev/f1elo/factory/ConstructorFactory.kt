package com.barbzdev.f1elo.factory

import com.barbzdev.f1elo.domain.Constructor.Companion.create

object ConstructorFactory {
  private val constructors = listOf(
    create(
      id = "mercedes",
      name = "Mercedes",
      nationality = "German",
      infoUrl = "http://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One"
    ),
    create(
      id = "ferrari",
      name = "Ferrari",
      nationality = "Italian",
      infoUrl = "http://en.wikipedia.org/wiki/Scuderia_Ferrari"
    ),
    create(
      id = "red_bull",
      name = "Red Bull Racing",
      nationality = "Austrian",
      infoUrl = "http://en.wikipedia.org/wiki/Red_Bull_Racing"
    ),
    create(
      id = "mclaren",
      name = "McLaren",
      nationality = "British",
      infoUrl = "http://en.wikipedia.org/wiki/McLaren"
    ),
    create(
      id = "aston_martin",
      name = "Aston Martin",
      nationality = "British",
      infoUrl = "http://en.wikipedia.org/wiki/Aston_Martin_in_Formula_One"
    )
  )

  fun aConstructor() = constructors.random()
}
