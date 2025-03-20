package com.barbzdev.sportselo.infrastructure.observability

import com.barbzdev.sportselo.domain.observability.MetricClient
import com.barbzdev.sportselo.domain.observability.MetricEnum
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
      MetricEnum.USE_CASE_SUCCESS -> "backend.f1elo.use_case.success"
      MetricEnum.USE_CASE_FAILURE -> "backend.f1elo.use_case.failure"
      MetricEnum.USE_CASE_ELAPSED_TIME -> "backend.f1elo.use_case.execution_time"
    }
}
