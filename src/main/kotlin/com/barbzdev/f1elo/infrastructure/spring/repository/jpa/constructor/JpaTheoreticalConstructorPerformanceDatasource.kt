package com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor

import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.season.SeasonEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaTheoreticalConstructorPerformanceDatasource :
  JpaRepository<TheoreticalConstructorPerformanceEntity, String> {
  fun findAllBySeason(season: SeasonEntity): List<TheoreticalConstructorPerformanceEntity>

  fun deleteAllBySeason(season: SeasonEntity)
}

@Entity
@Table(name = "theoretical_constructor_performance")
data class TheoreticalConstructorPerformanceEntity(
  @Id val id: String,
  @ManyToOne @JoinColumn(name = "constructor_id") val constructor: ConstructorEntity,
  @ManyToOne @JoinColumn(name = "season_id") val season: SeasonEntity,
  @Column(name = "theoretical_performance") val theoreticalPerformance: Float,
  @Column(name = "is_analyzed_season") val isAnalyzedSeason: Boolean,
  @Column(name = "data_origin_source") val dataOriginSource: String?,
  @Column(name = "data_origin_url") val dataOriginUrl: String?,
)
