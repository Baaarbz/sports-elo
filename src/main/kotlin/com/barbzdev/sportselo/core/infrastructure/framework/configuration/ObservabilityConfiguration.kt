package com.barbzdev.sportselo.core.infrastructure.framework.configuration

import com.barbzdev.sportselo.core.domain.observability.DefaultUseCaseInstrumentation
import com.barbzdev.sportselo.core.domain.observability.MetricClient
import com.barbzdev.sportselo.core.infrastructure.observability.MicrometerMetricClient
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObservabilityConfiguration {

  @Bean fun useCaseInstrumentation(metricClient: MetricClient) = DefaultUseCaseInstrumentation(metricClient)

  @Bean fun threadMetrics() = JvmThreadMetrics()

  @Bean fun metricClient(meterRegistry: MeterRegistry) = MicrometerMetricClient(meterRegistry)
}
