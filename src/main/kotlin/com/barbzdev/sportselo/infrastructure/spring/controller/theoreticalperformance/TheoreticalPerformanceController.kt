package com.barbzdev.sportselo.infrastructure.spring.controller.theoreticalperformance

import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceAlreadyCreated
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceConstructorPerformance
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceDataOrigin
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceOverANonExistentSeason
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceOverAnInvalidConstructor
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceOverAnInvalidPerformance
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceRequest
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceSuccess
import com.barbzdev.sportselo.application.theoreticalperformance.AddTheoreticalPerformanceUseCase
import com.barbzdev.sportselo.application.theoreticalperformance.DeleteTheoreticalPerformanceBySeasonYearRequest
import com.barbzdev.sportselo.application.theoreticalperformance.DeleteTheoreticalPerformanceBySeasonYearSuccess
import com.barbzdev.sportselo.application.theoreticalperformance.DeleteTheoreticalPerformanceBySeasonYearUseCase
import com.barbzdev.sportselo.application.theoreticalperformance.GetTheoreticalPerformanceBySeasonYearNotFound
import com.barbzdev.sportselo.application.theoreticalperformance.GetTheoreticalPerformanceBySeasonYearRequest
import com.barbzdev.sportselo.application.theoreticalperformance.GetTheoreticalPerformanceBySeasonYearSuccess
import com.barbzdev.sportselo.application.theoreticalperformance.GetTheoreticalPerformanceBySeasonYearUseCase
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
  private val deleteTheoreticalPerformanceBySeasonYearUseCase: DeleteTheoreticalPerformanceBySeasonYearUseCase,
  private val getTheoreticalPerformanceBySeasonYearUseCase: GetTheoreticalPerformanceBySeasonYearUseCase
) : TheoreticalPerformanceControllerDocumentation {

  @PostMapping
  override fun addTheoreticalPerformanceOfSeason(
    @RequestBody body: HttpTheoreticalPerformanceRequest
  ): ResponseEntity<Unit> {
    val response = addTheoreticalPerformanceUseCase(body.mapToUseCaseRequest())
    return when (response) {
      is AddTheoreticalPerformanceSuccess -> status(201).build()
      is AddTheoreticalPerformanceOverANonExistentSeason -> notFound().build()
      is AddTheoreticalPerformanceAlreadyCreated -> status(409).build()
      is AddTheoreticalPerformanceOverAnInvalidConstructor,
      AddTheoreticalPerformanceOverAnInvalidPerformance -> badRequest().build()
    }
  }

  @DeleteMapping("{seasonYear}")
  override fun deleteTheoreticalPerformanceBySeasonYear(@PathVariable seasonYear: Int): ResponseEntity<Unit> {
    val response =
      deleteTheoreticalPerformanceBySeasonYearUseCase.invoke(
        DeleteTheoreticalPerformanceBySeasonYearRequest(seasonYear))
    return when (response) {
      is DeleteTheoreticalPerformanceBySeasonYearSuccess -> status(200).build()
    }
  }

  @GetMapping("{seasonYear}")
  override fun getTheoreticalPerformanceOfSeasonYear(
    @PathVariable seasonYear: String
  ): ResponseEntity<HttpGetTheoreticalPerformanceBySeasonYearResponse> {
    val response =
      getTheoreticalPerformanceBySeasonYearUseCase.invoke(
        GetTheoreticalPerformanceBySeasonYearRequest(seasonYear.toInt()))
    return when (response) {
      is GetTheoreticalPerformanceBySeasonYearSuccess -> status(200).body(response.mapToHttpResponse())
      is GetTheoreticalPerformanceBySeasonYearNotFound -> notFound().build()
    }
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

  private fun GetTheoreticalPerformanceBySeasonYearSuccess.mapToHttpResponse() =
    HttpGetTheoreticalPerformanceBySeasonYearResponse(
      seasonYear = seasonYear,
      isAnalyzedData = isAnalyzedData,
      dataOrigin = HttpDataOrigin(source = dataOrigin!!.source, url = dataOrigin.url),
      theoreticalConstructorPerformances =
        theoreticalConstructorPerformances.map {
          HttpTheoreticalConstructorPerformance(constructorId = it.constructorId, performance = it.performance)
        })
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
