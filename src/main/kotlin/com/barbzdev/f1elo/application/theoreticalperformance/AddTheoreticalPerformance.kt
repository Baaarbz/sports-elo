package com.barbzdev.f1elo.application.theoreticalperformance

import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation

class AddTheoreticalPerformance(private val instrumentation: UseCaseInstrumentation) {

  operator fun invoke(request: AddTheoreticalPerformanceRequest): AddTheoreticalPerformanceResponse = instrumentation {
    TODO("Not yet implemented")
  }
}

data object AddTheoreticalPerformanceRequest

sealed class AddTheoreticalPerformanceResponse
