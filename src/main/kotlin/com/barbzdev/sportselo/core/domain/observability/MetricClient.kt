package com.barbzdev.sportselo.core.domain.observability

import java.time.Duration

interface MetricClient {
  fun increment(metric: MetricEnum, tags: Map<String, String> = emptyMap())

  fun recordTime(metric: MetricEnum, elapsedTime: Duration, tags: Map<String, String> = emptyMap())
}

enum class MetricEnum {
  USE_CASE_SUCCESS,
  USE_CASE_FAILURE,
  USE_CASE_ELAPSED_TIME,
}
