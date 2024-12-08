package com.barbzdev.f1elo.infrastructure.spring.controller.driver

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity

interface DriverControllerDocumentation {

  @Operation(summary = "Get driver details", description = "Fetch details of a driver by their ID")
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved driver details",
          content =
            [Content(mediaType = "application/json", schema = Schema(implementation = HttpGetDriverResponse::class))]),
        ApiResponse(responseCode = "404", description = "Driver not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun getDriver(driverId: String): ResponseEntity<HttpGetDriverResponse>

  @Operation(
    summary = "Get drivers peak ELO listing",
    description = "Fetches all the drivers peak ELO with pagination",
    parameters =
      [
        Parameter(name = "page", description = "Page number from 0 to (nPages - 1)", required = false, example = "0"),
        Parameter(
          name = "pageSize",
          description = "Number of items per page, supported values: [10 | 25 | 50 | 100]",
          required = false,
        ),
        Parameter(
          name = "sortBy",
          description = "Sort by field, supported values: [currentElo | highestElo | lowestElo | id]",
          required = false),
        Parameter(name = "sortOrder", description = "Sort order, supported values: [asc | desc]", required = false)])
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved drivers listing",
          content =
            [
              Content(
                mediaType = "application/json",
                schema = Schema(implementation = HttpGetDriverListingResponse::class))]),
        ApiResponse(
          responseCode = "400",
          description = "Required query params not present or not supported values",
          content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun getDriversListing(
    page: Int,
    pageSize: Int,
    sortBy: String,
    sortOrder: String
  ): ResponseEntity<HttpGetDriverListingResponse>
}
