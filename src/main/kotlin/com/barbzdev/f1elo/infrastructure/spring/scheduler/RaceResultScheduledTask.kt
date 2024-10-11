package com.barbzdev.f1elo.infrastructure.spring.scheduler

import com.barbzdev.f1elo.application.GatherRaceResultUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RaceResultScheduledTask(
  private val gatherRaceResultUseCase: GatherRaceResultUseCase
) {

  @Scheduled(cron = "0 0 12 * * ?")
  fun gatherRaceResults() = gatherRaceResultUseCase.invoke()
}
