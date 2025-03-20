package com.barbzdev.sportselo.domain.observability

interface UseCaseInstrumentation {
  operator fun <Response> invoke(useCase: () -> Response): Response
}
