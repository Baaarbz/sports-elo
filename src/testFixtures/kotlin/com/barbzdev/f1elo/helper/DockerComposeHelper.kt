package com.barbzdev.f1elo.helper

import java.io.File
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import org.testcontainers.containers.wait.strategy.WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY

class DockerComposeHelper {

  companion object {
    fun create(): ComposeContainer {
      return ComposeContainer(File("docker-compose.yml"))
        .apply { withLocalCompose(true) }
        .apply { waitingFor("postgres", WaitAllStrategy(WITH_INDIVIDUAL_TIMEOUTS_ONLY)) }
    }
  }
}
