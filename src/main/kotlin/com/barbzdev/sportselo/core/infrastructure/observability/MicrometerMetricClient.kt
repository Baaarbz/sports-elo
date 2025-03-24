package com.barbzdev.sportselo.core.infrastructure.observability

import com.barbzdev.sportselo.core.domain.observability.MetricClient
import com.barbzdev.sportselo.core.domain.observability.MetricEnum
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import java.time.Duration

class MicrometerMetricClient(private val meterRegistry: MeterRegistry) : MetricClient {

  override fun increment(metric: MetricEnum, tags: Map<String, String>) {
    val micrometerTags = tags.map { (key, value) -> Tag.of(key, value) }
    meterRegistry.counter(metric.toMetricValue(), micrometerTags).increment()
  }

  override fun recordTime(metric: MetricEnum, elapsedTime: Duration, tags: Map<String, String>) {
    val micrometerTags = tags.map { (key, value) -> Tag.of(key, value) }
    meterRegistry.timer(metric.toMetricValue(), micrometerTags).record(elapsedTime)
  }

  private fun MetricEnum.toMetricValue() =
    when (this) {
      MetricEnum.USE_CASE_SUCCESS -> "backend.sportselo.use_case.success"
      MetricEnum.USE_CASE_FAILURE -> "backend.sportselo.use_case.failure"
      MetricEnum.USE_CASE_ELAPSED_TIME -> "backend.sportselo.use_case.execution_time"
    }
}
