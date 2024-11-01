package com.barbzdev.f1elo.infrastructure.spring.scheduler

import com.barbzdev.f1elo.application.GatherRacesBySeasonUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RacesScheduledTask(
  private val gatherRacesBySeasonUseCase: GatherRacesBySeasonUseCase, @Value("\${cron.job.enabled}") private val isCronJobEnabled: Boolean
) {

  @Scheduled(cron = "0 0 12 * * ?")
  fun gatherRacesResultBySeasonScheduledTask() {
    if (isCronJobEnabled) {
      gatherRacesBySeasonUseCase.invoke()
    }
  }
}
