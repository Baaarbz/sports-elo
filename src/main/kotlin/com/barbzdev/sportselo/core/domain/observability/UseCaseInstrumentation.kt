package com.barbzdev.sportselo.core.domain.observability

interface UseCaseInstrumentation {
  operator fun <Response> invoke(useCase: () -> Response): Response
}
