package com.barbzdev.sportselo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableConfigurationProperties
@EnableAsync
@SpringBootApplication(
  scanBasePackages =
    [
      "com.barbzdev.sportselo.core.infrastructure.framework",
      "com.barbzdev.sportselo.formulaone.infrastructure.framework",
    ])
class SportsEloApplication

fun main(args: Array<String>) {
  runApplication<SportsEloApplication>(*args)
}
