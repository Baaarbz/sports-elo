package com.barbzdev.sportselo.formulaone.infrastructure.framework.scheduler

import com.barbzdev.sportselo.formulaone.application.ReplicateSeasonByYearUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RacesScheduledTask(
  private val replicateSeasonByYearUseCase: ReplicateSeasonByYearUseCase,
  @Value("\${cron.job.enabled}") private val isCronJobEnabled: Boolean
) {

  @Scheduled(cron = "0 0 */6 * * ?")
  fun gatherRacesResultBySeasonScheduledTask() {
    if (isCronJobEnabled) {
      replicateSeasonByYearUseCase.invoke()
    }
  }
}
