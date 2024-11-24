package com.barbzdev.f1elo.infrastructure.spring.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity

interface DriverControllerDocumentation {

  @Operation(summary = "Get driver ELO details", description = "Fetches the ELO details of a driver by their ID")
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved driver ELO details",
          content =
            [Content(mediaType = "application/json", schema = Schema(implementation = HttpGetDriverResponse::class))]),
        ApiResponse(responseCode = "404", description = "Driver not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun getDriver(driverId: String): ResponseEntity<HttpGetDriverResponse>

  @Operation(summary = "Get drivers peak ELO listing", description = "Fetches all the drivers peak ELO with pagination")
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
  fun getDriversListing(page: Int, pageSize: Int): ResponseEntity<HttpGetDriverListingResponse>
}
