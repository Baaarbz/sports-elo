package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.domain.observability.MetricClient
import com.barbzdev.f1elo.infrastructure.observability.DefaultUseCaseInstrumentation
import com.barbzdev.f1elo.infrastructure.observability.MicrometerMetricClient
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObservabilityConfiguration {

  @Bean
  fun useCaseInstrumentation(metricClient: MetricClient) = DefaultUseCaseInstrumentation(metricClient)

  @Bean
  fun threadMetrics() = JvmThreadMetrics()

  @Bean
  fun metricClient(meterRegistry: MeterRegistry) = MicrometerMetricClient(meterRegistry)
}
