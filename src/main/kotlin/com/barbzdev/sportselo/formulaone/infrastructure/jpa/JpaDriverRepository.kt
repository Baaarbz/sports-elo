package com.barbzdev.sportselo.formulaone.infrastructure.jpa

import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.domain.DriverId
import com.barbzdev.sportselo.core.domain.util.DomainPaginated
import com.barbzdev.sportselo.core.domain.util.Page
import com.barbzdev.sportselo.core.domain.util.PageSize
import com.barbzdev.sportselo.core.domain.util.SortBy
import com.barbzdev.sportselo.core.domain.util.SortOrder
import com.barbzdev.sportselo.formulaone.domain.repository.DriverRepository
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DomainToEntityMapper.toEntity
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverEloHistoryDatasource
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.JpaDriverIRatingHistoryDatasource
import kotlin.jvm.optionals.getOrNull
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class JpaDriverRepository(
  private val driverDatasource: JpaDriverDatasource,
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource,
  private val iRatingHistoryDatasource: JpaDriverIRatingHistoryDatasource
) : DriverRepository {
  override fun findAll(page: Page, pageSize: PageSize, sortBy: SortBy, sortOrder: SortOrder): DomainPaginated<Driver> {
    val orderByColumn =
      when (sortBy.value) {
        "currentElo" -> "current_elo"
        "highestElo" -> "highest_elo"
        "lowestElo" -> "lowest_elo"
        "currentIRating" -> "current_irating"
        "highestIRating" -> "highest_irating"
        "lowestIRating" -> "lowest_irating"
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

    val jpaPaginated = driverDatasource.findAllJoinDriverRatingsHistory(pageable)
    return DomainPaginated(
      elements =
        jpaPaginated.toList().map { driverEntity ->
          val eloRecordEntity = eloHistoryDatasource.findAllByDriver(driverEntity)
          val iRatingRecordEntity = iRatingHistoryDatasource.findAllByDriver(driverEntity)
          driverEntity.toDomain(eloRecordEntity, iRatingRecordEntity)
        },
      page = page.value,
      pageSize = pageSize.value,
      totalElements = jpaPaginated.totalElements,
      totalPages = jpaPaginated.totalPages)
  }

  override fun findAll(): List<Driver> =
    driverDatasource.findAll().map { driverEntity ->
      val eloRecordEntity = eloHistoryDatasource.findAllByDriver(driverEntity)
      val iRatingRecordEntity = iRatingHistoryDatasource.findAllByDriver(driverEntity)
      driverEntity.toDomain(eloRecordEntity, iRatingRecordEntity)
    }

  override fun findBy(id: DriverId): Driver? =
    driverDatasource.findById(id.value).getOrNull()?.let {
      val eloRecordEntity = eloHistoryDatasource.findAllByDriver(it)
      val iRatingRecordEntity = iRatingHistoryDatasource.findAllByDriver(it)
      it.toDomain(eloRecordEntity, iRatingRecordEntity)
    }

  override fun save(driver: Driver) {
    driverDatasource.save(driver.toEntity())
    eloHistoryDatasource.saveAll(driver.eloRecord().map { it.toEntity(driver) })
    iRatingHistoryDatasource.saveAll(driver.iRatingRecord().map { it.toEntity(driver) })
  }
}
