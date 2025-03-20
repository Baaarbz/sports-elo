package com.barbzdev.sportselo.infrastructure.spring.configuration

import com.barbzdev.sportselo.application.data.ReprocessEloUseCase
import com.barbzdev.sportselo.application.data.ReprocessIRatingUseCase
import com.barbzdev.sportselo.infrastructure.spring.event.listener.RatingReprocessingEventListener
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
