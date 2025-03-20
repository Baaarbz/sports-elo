package com.barbzdev.sportselo.domain.exception

data class NonValidPerformanceException(val performance: Float) :
  RuntimeException("Performance value must be greater than or equal to 0. Value received: $performance")
