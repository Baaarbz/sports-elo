package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.GatherRaceResultUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {

  @Bean
  fun gatherRaceResultUseCase() = GatherRaceResultUseCase()
}
