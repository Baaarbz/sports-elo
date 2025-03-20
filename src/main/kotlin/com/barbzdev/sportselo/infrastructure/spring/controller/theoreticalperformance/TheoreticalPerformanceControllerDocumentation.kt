package com.barbzdev.sportselo.infrastructure.spring.controller.theoreticalperformance

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity

interface TheoreticalPerformanceControllerDocumentation {
  @Operation(
    summary = "Add theoretical performance of a season",
    description = "Add theoretical performance of the different constructors by season to the database")
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "201",
          description = "Successfully add theoretical performance of a season",
          content = [Content()]),
        ApiResponse(responseCode = "404", description = "Season not found", content = [Content()]),
        ApiResponse(
          responseCode = "409",
          description = "Theoretical performance already existent for given season",
          content = [Content()]),
        ApiResponse(
          responseCode = "400",
          description =
            "The request contains not valid data, for example, non existent constructor, not valid performance",
          content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun addTheoreticalPerformanceOfSeason(body: HttpTheoreticalPerformanceRequest): ResponseEntity<Unit>

  @Operation(
    summary = "Delete theoretical performance of a season",
    description = "Delete theoretical performance by season year from the database")
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "200",
          description = "Successfully deleted theoretical performance of a season",
          content = [Content()]),
        ApiResponse(responseCode = "404", description = "Season not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun deleteTheoreticalPerformanceBySeasonYear(seasonYear: Int): ResponseEntity<Unit>

  @Operation(
    summary = "Get theoretical performance of a season",
    description = "Retrieve theoretical performance of the different constructors by season from the database")
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved theoretical performance of a season",
          content = [Content()]),
        ApiResponse(responseCode = "401", description = "Season not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun getTheoreticalPerformanceOfSeasonYear(
    seasonYear: String
  ): ResponseEntity<HttpGetTheoreticalPerformanceBySeasonYearResponse>
}
