package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.DriverId
import com.barbzdev.f1elo.domain.common.DomainPaginated
import com.barbzdev.f1elo.domain.common.Page
import com.barbzdev.f1elo.domain.common.PageSize
import com.barbzdev.f1elo.domain.common.SortBy
import com.barbzdev.f1elo.domain.common.SortOrder

interface DriverRepository {
  fun findAll(page: Page, pageSize: PageSize, sortBy: SortBy, sortOrder: SortOrder): DomainPaginated<Driver>

  fun findAll(): List<Driver>

  fun findBy(id: DriverId): Driver?

  fun save(driver: Driver)
}
