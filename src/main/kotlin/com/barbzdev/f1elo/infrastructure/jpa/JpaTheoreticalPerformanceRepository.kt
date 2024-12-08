package com.barbzdev.f1elo.infrastructure.jpa

import com.barbzdev.f1elo.domain.TheoreticalPerformance
import com.barbzdev.f1elo.domain.common.SeasonYear
import com.barbzdev.f1elo.domain.repository.TheoreticalPerformanceRepository
import com.barbzdev.f1elo.infrastructure.mapper.DomainToEntityMapper.mapToTheoreticalConstructorPerformanceEntity
import com.barbzdev.f1elo.infrastructure.mapper.EntityToDomainMapper.toDomain
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.JpaConstructorDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.JpaTheoreticalConstructorPerformanceDatasource
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.season.JpaSeasonDatasource

class JpaTheoreticalPerformanceRepository(
  private val theoreticalConstructorPerformanceDatasource: JpaTheoreticalConstructorPerformanceDatasource,
  private val seasonDatasource: JpaSeasonDatasource,
  private val constructorDatasource: JpaConstructorDatasource
) : TheoreticalPerformanceRepository {
  override fun deleteBy(season: SeasonYear) {
    TODO("Not yet implemented")
  }

  override fun findBy(season: SeasonYear): TheoreticalPerformance? {
    val seasonEntity = seasonDatasource.findByYear(season.value) ?: return null
    return theoreticalConstructorPerformanceDatasource.findAllBySeason(seasonEntity).toDomain()
  }

  override fun save(theoreticalPerformance: TheoreticalPerformance) {
    theoreticalPerformance.constructorsPerformance().forEach {
      val constructor = constructorDatasource.findById(it.constructor.id().value).get()
      val season = seasonDatasource.findByYear(theoreticalPerformance.seasonYear().value)!!
      theoreticalConstructorPerformanceDatasource.save(
        mapToTheoreticalConstructorPerformanceEntity(theoreticalPerformance, constructor, season)
      )
    }
  }
}
