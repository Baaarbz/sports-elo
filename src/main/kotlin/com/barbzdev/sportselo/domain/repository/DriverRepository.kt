package com.barbzdev.sportselo.domain.repository

import com.barbzdev.sportselo.domain.Driver
import com.barbzdev.sportselo.domain.DriverId
import com.barbzdev.sportselo.domain.common.DomainPaginated
import com.barbzdev.sportselo.domain.common.Page
import com.barbzdev.sportselo.domain.common.PageSize
import com.barbzdev.sportselo.domain.common.SortBy
import com.barbzdev.sportselo.domain.common.SortOrder

interface DriverRepository {
  fun findAll(page: Page, pageSize: PageSize, sortBy: SortBy, sortOrder: SortOrder): DomainPaginated<Driver>

  fun findAll(): List<Driver>

  fun findBy(id: DriverId): Driver?

  fun save(driver: Driver)
}
