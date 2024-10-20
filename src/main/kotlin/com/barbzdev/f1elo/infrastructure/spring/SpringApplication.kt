package com.barbzdev.f1elo.infrastructure.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = ["com.barbzdev.f1elo.infrastructure.spring"])
class SpringApplication

fun main(args: Array<String>) {
  runApplication<SpringApplication>(*args)
}
