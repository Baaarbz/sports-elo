package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.infrastructure.observability.DefaultUseCaseInstrumentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObservabilityConfiguration {

  @Bean
  fun useCaseInstrumentation() = DefaultUseCaseInstrumentation()
}
