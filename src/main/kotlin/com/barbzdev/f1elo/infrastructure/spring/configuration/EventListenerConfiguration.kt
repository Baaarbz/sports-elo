package com.barbzdev.f1elo.infrastructure.spring.configuration

import com.barbzdev.f1elo.application.data.ReprocessEloUseCase
import com.barbzdev.f1elo.application.data.ReprocessIRatingUseCase
import com.barbzdev.f1elo.infrastructure.spring.event.listener.RatingReprocessingEventListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventListenerConfiguration {

  @Bean
  fun ratingReprocessingEventListener(
    reprocessEloUseCase: ReprocessEloUseCase,
    reprocessIRatingUseCase: ReprocessIRatingUseCase
  ) = RatingReprocessingEventListener(reprocessEloUseCase, reprocessIRatingUseCase)
}
