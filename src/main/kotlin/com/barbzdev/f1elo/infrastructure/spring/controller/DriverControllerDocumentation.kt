package com.barbzdev.f1elo.infrastructure.spring.controller

import org.springframework.http.ResponseEntity


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

interface DriverControllerDocumentation {

  @Operation(summary = "Get driver ELO details", description = "Fetches the ELO details of a driver by their ID")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "Successfully retrieved driver ELO details", content = [Content(mediaType = "application/json", schema = Schema(implementation = HttpGetDriverResponse::class))]),
    ApiResponse(responseCode = "404", description = "Driver not found", content = [Content()]),
    ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
  ])
  fun getDriver(driverId: String): ResponseEntity<HttpGetDriverResponse>
}
