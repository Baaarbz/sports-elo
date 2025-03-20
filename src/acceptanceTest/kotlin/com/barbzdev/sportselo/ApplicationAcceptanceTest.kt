package com.barbzdev.sportselo

import com.barbzdev.sportselo.helper.DockerComposeHelper
import com.barbzdev.sportselo.testcases.AddTheoreticalPerformanceShould
import com.barbzdev.sportselo.testcases.CalculateEloOfDriversBySeasonShould
import com.barbzdev.sportselo.testcases.GetDriverByIdShould
import com.barbzdev.sportselo.testcases.ListingDriversShould
import org.junit.jupiter.api.Nested
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ApplicationAcceptanceTest {

  companion object {
    @Container val dockerContainer = DockerComposeHelper.create()
  }

  @Nested inner class CalculateEloOfDriversBySeason : CalculateEloOfDriversBySeasonShould()

  @Nested inner class ListingDrivers : ListingDriversShould()

  @Nested inner class GetDriverById : GetDriverByIdShould()

  @Nested inner class AddTheoreticalPerformance : AddTheoreticalPerformanceShould()
}
