package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.Driver
import com.barbzdev.f1elo.domain.DriverId
import com.barbzdev.f1elo.domain.common.DomainPaginated
import com.barbzdev.f1elo.domain.common.Page
import com.barbzdev.f1elo.domain.common.PageSize
import com.barbzdev.f1elo.domain.common.SortBy
import com.barbzdev.f1elo.domain.common.SortOrder
import com.barbzdev.f1elo.domain.repository.DriverRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.f1elo.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.JpaDriverEloHistoryDatasource
import kotlin.jvm.optionals.getOrNull
import org.springframework.data.domain.PageRequest

class JpaDriverRepository(
  private val driverDatasource: JpaDriverDatasource,
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource
) : DriverRepository {
  override fun findAll(page: Page, pageSize: PageSize, sortBy: SortBy, sortOrder: SortOrder): DomainPaginated<Driver> {
    val pageable = PageRequest.of(page.value, pageSize.value)
    val jpaPaginated = driverDatasource.findAll(pageable)
    return DomainPaginated(
      elements =
        jpaPaginated.toList().map { driverEntity ->
          val eloRecordEntity = eloHistoryDatasource.findAllByDriver(driverEntity)
          driverEntity.toDomain(eloRecordEntity)
        },
      page = page.value,
      pageSize = pageSize.value,
      totalElements = jpaPaginated.totalElements,
      totalPages = jpaPaginated.totalPages)
  }

  override fun findBy(id: DriverId): Driver? =
    driverDatasource.findById(id.value).getOrNull()?.let {
      val eloRecordEntity = eloHistoryDatasource.findAllByDriver(it)
      it.toDomain(eloRecordEntity)
    }

  override fun save(driver: Driver) {
    driverDatasource.save(driver.toEntity())
    eloHistoryDatasource.saveAll(driver.eloRecord().map { it.toEntity(driver) })
  }
}
