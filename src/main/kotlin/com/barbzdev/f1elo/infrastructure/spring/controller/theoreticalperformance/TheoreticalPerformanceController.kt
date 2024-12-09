package com.barbzdev.f1elo.infrastructure.spring.controller.theoreticalperformance

import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceAlreadyCreated
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceConstructorPerformance
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceDataOrigin
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceOverANonExistentSeason
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceOverAnInvalidConstructor
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceOverAnInvalidPerformance
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceRequest
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceSuccess
import com.barbzdev.f1elo.application.theoreticalperformance.AddTheoreticalPerformanceUseCase
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/theoretical-performance")
class TheoreticalPerformanceController(
  private val addTheoreticalPerformanceUseCase: AddTheoreticalPerformanceUseCase,
) : TheoreticalPerformanceControllerDocumentation {

  @PostMapping
  override fun addTheoreticalPerformanceOfSeason(
    @RequestBody body: HttpTheoreticalPerformanceRequest
  ): ResponseEntity<Unit> {
    val response = addTheoreticalPerformanceUseCase(body.mapToUseCaseRequest())
    return when (response) {
      is AddTheoreticalPerformanceSuccess -> status(201).build()
      is AddTheoreticalPerformanceOverANonExistentSeason -> notFound().build()
      is AddTheoreticalPerformanceAlreadyCreated,
      AddTheoreticalPerformanceOverAnInvalidConstructor,
      AddTheoreticalPerformanceOverAnInvalidPerformance -> badRequest().build()
    }
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

  private fun HttpTheoreticalPerformanceRequest.mapToUseCaseRequest() =
    AddTheoreticalPerformanceRequest(
      seasonYear = seasonYear,
      isAnalyzedData = isAnalyzedData,
      theoreticalConstructorPerformances =
        theoreticalConstructorPerformances.map {
          AddTheoreticalPerformanceConstructorPerformance(
            constructorId = it.constructorId, performance = it.performance)
        },
      dataOrigin = AddTheoreticalPerformanceDataOrigin(source = dataOrigin.source, url = dataOrigin.url))
}

data class HttpTheoreticalPerformanceRequest(
  val seasonYear: Int,
  val isAnalyzedData: Boolean,
  val dataOrigin: HttpDataOrigin,
  val theoreticalConstructorPerformances: List<HttpTheoreticalConstructorPerformance>
)

data class HttpTheoreticalConstructorPerformance(val constructorId: String, val performance: Float)

data class HttpDataOrigin(val source: String, val url: String)

data class HttpGetTheoreticalPerformanceBySeasonYearResponse(
  val seasonYear: Int,
  val isAnalyzedData: Boolean,
  val dataOrigin: HttpDataOrigin,
  val theoreticalConstructorPerformances: List<HttpTheoreticalConstructorPerformance>
)
