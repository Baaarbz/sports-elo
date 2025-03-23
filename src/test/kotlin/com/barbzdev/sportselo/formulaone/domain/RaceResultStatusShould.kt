package com.barbzdev.sportselo.formulaone.domain

import com.barbzdev.sportselo.formulaone.domain.valueobject.race.RaceResultStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RaceResultStatusShould {
  @ParameterizedTest(name = "{index} => text result={0}, expected result={1}")
  @CsvSource(
    "Finished, FINISHED",
    "Disqualified, DISQUALIFIED",
    "Accident, ACCIDENT",
    "Collision, COLLISION",
    "Engine, ENGINE",
    "Gearbox, GEARBOX",
    "Transmission, TRANSMISSION",
    "Clutch, CLUTCH",
    "Hydraulics, HYDRAULICS",
    "Electrical, ELECTRICAL",
    "+1 Lap, LAPS_1",
    "+2 Laps, LAPS_2",
    "+3 Laps, LAPS_3",
    "+4 Laps, LAPS_4",
    "+5 Laps, LAPS_5",
    "+6 Laps, LAPS_6",
    "+7 Laps, LAPS_7",
    "+8 Laps, LAPS_8",
    "+9 Laps, LAPS_9",
    "Spun off, SPUN_OFF",
    "Radiator, RADIATOR",
    "Suspension, SUSPENSION",
    "Brakes, BRAKES",
    "Differential, DIFFERENTIAL",
    "Overheating, OVERHEATING",
    "Mechanical, MECHANICAL",
    "Tyre, TYRE",
    "Driver Seat, DRIVER_SEAT",
    "Puncture, PUNCTURE",
    "Driveshaft, DRIVESHAFT")
  fun `return correct race result for given result text`(textResult: String, expectedResult: String) {
    val raceResult = RaceResultStatus.fromText(textResult)

    assertThat(raceResult.name).isEqualTo(expectedResult)
  }
}
