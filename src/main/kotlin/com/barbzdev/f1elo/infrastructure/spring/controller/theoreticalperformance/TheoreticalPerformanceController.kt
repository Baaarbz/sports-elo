package com.barbzdev.f1elo.infrastructure.spring.controller.theoreticalperformance

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/theoretical-performance")
class TheoreticalPerformanceController : TheoreticalPerformanceControllerDocumentation {

  @PostMapping
  override fun addTheoreticalPerformanceOfSeason(body: HttpTheoreticalPerformanceRequest): ResponseEntity<Unit> {
    TODO("Not yet implemented")
  }

  @DeleteMapping("{seasonYear}")
  override fun deleteTheoreticalPerformanceBySeasonYear(@PathVariable seasonYear: String): ResponseEntity<Unit> {
    TODO("Not yet implemented")
  }

  @GetMapping("{seasonYear}")
  override fun getTheoreticalPerformanceOfSeasonYear(
    @PathVariable seasonYear: String
  ): ResponseEntity<HttpGetTheoreticalPerformanceBySeasonYearResponse> {
    TODO("Not yet implemented")
  }
}

data class HttpTheoreticalPerformanceRequest(
  val seasonYear: String,
  val isAnalyzedData: Boolean,
  val theoreticalConstructorPerformances: List<HttpTheoreticalConstructorPerformance>
)

data class HttpTheoreticalConstructorPerformance(val constructorId: String, val performance: Float)

data class HttpGetTheoreticalPerformanceBySeasonYearResponse(
  val seasonYear: String,
  val isAnalyzedData: Boolean,
  val theoreticalConstructorPerformances: List<HttpTheoreticalConstructorPerformance>
)
