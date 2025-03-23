package com.barbzdev.sportselo.formulaone.infrastructure.framework.configuration

import com.barbzdev.sportselo.formulaone.application.ReprocessEloUseCase
import com.barbzdev.sportselo.formulaone.infrastructure.framework.event.RatingReprocessingEventListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventListenerConfiguration {

  @Bean
  fun ratingReprocessingEventListener(reprocessEloUseCase: ReprocessEloUseCase) =
    RatingReprocessingEventListener(reprocessEloUseCase)
}
