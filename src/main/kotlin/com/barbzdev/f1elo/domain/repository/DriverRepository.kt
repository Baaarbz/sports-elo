package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.DriverId

interface DriverRepository {
  fun findBy(driver: DriverId): Driver?

  fun save(driver: Driver)
}
