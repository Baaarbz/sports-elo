package com.barbzdev.sportselo.formulaone.infrastructure.framework.controller.data

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity

interface DataControllerDocumentation {
  @Operation(
    summary = "Reprocess ratings",
    description =
      "Reprocess the ratings of all drivers, removes the current data of the selected ratings systems and reprocess them.",
    requestBody =
      RequestBody(
        content =
          [
            Content(
              mediaType = "application/json",
              schema = Schema(implementation = HttpReprocessRatingsRequest::class),
            ),
          ],
      ),
  )
  @ApiResponses(
    value =
      [
        ApiResponse(
          responseCode = "202",
          description = "The request was accepted and the selected ratings are being reprocessed",
          content = [Content()]),
        ApiResponse(responseCode = "400", description = "Non valid request", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])])
  fun startRatingsReprocessing(body: HttpReprocessRatingsRequest): ResponseEntity<Unit>
}
