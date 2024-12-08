package com.barbzdev.f1elo.domain.exception

data class NonValidPerformanceException(val performance: Float) :
  RuntimeException("Performance value must be greater than or equal to 0. Value received: $performance")
