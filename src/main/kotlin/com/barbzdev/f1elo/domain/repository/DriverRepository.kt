package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.DriverId
import com.barbzdev.f1elo.domain.common.DomainPaginated

interface DriverRepository {
  fun findAll(page: Int, pageSize: Int): DomainPaginated<Driver>

  fun findBy(id: DriverId): Driver?

  fun save(driver: Driver)
}
