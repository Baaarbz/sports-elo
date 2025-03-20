package com.barbzdev.sportselo.infrastructure.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableConfigurationProperties
@EnableAsync
@SpringBootApplication(scanBasePackages = ["com.barbzdev.sportselo.infrastructure.spring"])
class SpringApplication

fun main(args: Array<String>) {
  runApplication<SpringApplication>(*args)
}
