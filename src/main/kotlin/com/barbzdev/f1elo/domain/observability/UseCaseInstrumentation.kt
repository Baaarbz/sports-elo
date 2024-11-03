package com.barbzdev.f1elo.domain.observability

interface UseCaseInstrumentation {
  operator fun <Response> invoke(useCase: () -> Response): Response
}
