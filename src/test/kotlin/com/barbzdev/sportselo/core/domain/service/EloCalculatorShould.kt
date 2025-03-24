package com.barbzdev.sportselo.core.domain.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.sportselo.core.domain.service.Result.DRAW
import com.barbzdev.sportselo.core.domain.service.Result.LOSE
import com.barbzdev.sportselo.core.domain.service.Result.WIN
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn.Companion.now
import org.junit.jupiter.api.Test

class EloCalculatorShould {

  private val eloCalculator = EloCalculator()

  @Test
  fun `calculate elo 1v1 method WIN`() {
    val eloToUpdate = Elo(2000, now())
    val eloRival = Elo(2000, now())

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRival, WIN)

    assertThat(eloDelta).isEqualTo(16)
  }

  @Test
  fun `calculate elo 1v1 method DRAW`() {
    val eloToUpdate = Elo(2000, now())
    val eloRival = Elo(2000, now())

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRival, DRAW)

    assertThat(eloDelta).isEqualTo(0)
  }

  @Test
  fun `calculate elo 1v1 method LOSE`() {
    val eloToUpdate = Elo(2000, now())
    val eloRival = Elo(2000, now())

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRival, LOSE)

    assertThat(eloDelta).isEqualTo(-16)
  }

  @Test
  fun `calculate elo teams method WIN`() {
    val eloToUpdate = listOf(Elo(2000, now()), Elo(1000, now()), Elo(1250, now()))
    val eloRival = listOf(Elo(2000, now()), Elo(1000, now()), Elo(1250, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRival, WIN)

    assertThat(eloDelta).isEqualTo(16)
  }

  @Test
  fun `calculate elo teams method DRAW`() {
    val eloToUpdate = listOf(Elo(2000, now()), Elo(1000, now()), Elo(1250, now()))
    val eloRival = listOf(Elo(2000, now()), Elo(1000, now()), Elo(1250, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRival, DRAW)

    assertThat(eloDelta).isEqualTo(0)
  }

  @Test
  fun `calculate elo teams method LOSE`() {
    val eloToUpdate = listOf(Elo(2000, now()), Elo(1000, now()), Elo(1250, now()))
    val eloRival = listOf(Elo(2000, now()), Elo(1000, now()), Elo(1250, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRival, LOSE)

    assertThat(eloDelta).isEqualTo(-16)
  }

  @Test
  fun `calculate elo motorsports method first position`() {
    val eloToUpdate = Elo(2000, now())
    val eloRivals = listOf(Elo(2000, now()), Elo(2000, now()), Elo(2000, now()), Elo(2000, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRivals, 1)

    assertThat(eloDelta).isEqualTo(22)
  }

  @Test
  fun `calculate elo motorsports method second position`() {
    val eloToUpdate = Elo(2000, now())
    val eloRivals = listOf(Elo(2000, now()), Elo(2000, now()), Elo(2000, now()), Elo(2000, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRivals, 2)

    assertThat(eloDelta).isEqualTo(11)
  }

  @Test
  fun `calculate elo motorsports method third position`() {
    val eloToUpdate = Elo(2000, now())
    val eloRivals = listOf(Elo(2000, now()), Elo(2000, now()), Elo(2000, now()), Elo(2000, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRivals, 3)

    assertThat(eloDelta).isEqualTo(0)
  }

  @Test
  fun `calculate elo motorsports method fourth position`() {
    val eloToUpdate = Elo(2000, now())
    val eloRivals = listOf(Elo(2000, now()), Elo(2000, now()), Elo(2000, now()), Elo(2000, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRivals, 4)

    assertThat(eloDelta).isEqualTo(-11)
  }

  @Test
  fun `calculate elo motorsports method fifth position`() {
    val eloToUpdate = Elo(2000, now())
    val eloRivals = listOf(Elo(2000, now()), Elo(2000, now()), Elo(2000, now()), Elo(2000, now()))

    val eloDelta = eloCalculator.calculate(eloToUpdate, eloRivals, 5)

    assertThat(eloDelta).isEqualTo(-22)
  }
}
