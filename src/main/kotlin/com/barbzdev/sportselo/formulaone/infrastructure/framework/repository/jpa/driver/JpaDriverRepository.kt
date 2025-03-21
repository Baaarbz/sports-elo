package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver

import com.barbzdev.sportselo.core.domain.util.DomainPaginated
import com.barbzdev.sportselo.core.domain.util.Page
import com.barbzdev.sportselo.core.domain.util.PageSize
import com.barbzdev.sportselo.core.domain.util.SortBy
import com.barbzdev.sportselo.core.domain.util.SortOrder
import com.barbzdev.sportselo.core.domain.valueobject.SportsmanId
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DriverMapper
import kotlin.jvm.optionals.getOrNull
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class JpaDriverRepository(
  private val driverDatasource: JpaDriverDatasource,
  private val driverMapper: DriverMapper
) : DriverRepository {
  override fun findAll(page: Page, pageSize: PageSize, sortBy: SortBy, sortOrder: SortOrder): DomainPaginated<Driver> {
    val orderByColumn =
      when (sortBy.value) {
        "currentElo" -> "current_elo"
        "highestElo" -> "highest_elo"
        "lowestElo" -> "lowest_elo"
        "id" -> "id"
        else -> throw IllegalArgumentException("Invalid sortBy value for find all drivers query")
      }
    val sortDirection =
      when (sortOrder.value) {
        "asc" -> Sort.Direction.ASC
        "desc" -> Sort.Direction.DESC
        else -> throw IllegalArgumentException("Invalid sortOrder value for find all drivers query")
      }
    val pageable = PageRequest.of(page.value, pageSize.value, sortDirection, orderByColumn)

    val jpaPaginated = driverDatasource.findAllJoinDriverEloHistory(pageable)
    return DomainPaginated(
      elements = jpaPaginated.toList().map { driverEntity -> driverMapper.toDomain(driverEntity) },
      page = page.value,
      pageSize = pageSize.value,
      totalElements = jpaPaginated.totalElements,
      totalPages = jpaPaginated.totalPages
    )
  }

  override fun findAll(): List<Driver> = driverDatasource.findAllJoinDriverEloHistory().map { driverMapper.toDomain(it) }

  override fun findBy(id: SportsmanId): Driver? = driverDatasource.findByIdJoinDriverEloHistory(id.value)
    .getOrNull()
    ?.let { driverMapper.toDomain(it) }

  override fun save(driver: Driver) {
    driverDatasource.save(driverMapper.toEntity(driver))
  }

  override fun saveAll(drivers: List<Driver>) {
    drivers.map { driverMapper.toEntity(it) }.let { driverDatasource.saveAll(it) }
  }
}
