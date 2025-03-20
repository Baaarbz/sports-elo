package com.barbzdev.sportselo.formulaone.domain.repository

import com.barbzdev.sportselo.core.domain.util.DomainPaginated
import com.barbzdev.sportselo.core.domain.util.Page
import com.barbzdev.sportselo.core.domain.util.PageSize
import com.barbzdev.sportselo.core.domain.util.SortBy
import com.barbzdev.sportselo.core.domain.util.SortOrder
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId
import com.barbzdev.sportselo.formulaone.domain.Driver

interface DriverRepository {
  fun findAll(page: Page, pageSize: PageSize, sortBy: SortBy, sortOrder: SortOrder): DomainPaginated<Driver>

  fun findAll(): List<Driver>

  fun findBy(id: SportsmanId): Driver?

  fun save(driver: Driver)
}
