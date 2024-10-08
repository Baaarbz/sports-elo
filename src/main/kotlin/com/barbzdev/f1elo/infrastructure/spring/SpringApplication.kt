package com.barbzdev.f1elo.infrastructure.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class F1EloApplication

fun main(args: Array<String>) {
  runApplication<F1EloApplication>(*args)
}
