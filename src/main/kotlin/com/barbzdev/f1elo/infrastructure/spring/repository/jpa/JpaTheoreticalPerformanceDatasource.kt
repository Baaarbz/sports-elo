package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface JpaTheoreticalConstructorPerformanceDatasource : JpaRepository<TheoreticalConstructorPerformanceEntity, String>

@Entity
@Table(name = "theoretical_constructor_performance")
data class TheoreticalConstructorPerformanceEntity(
  @Id val id: String,
  @Column(name = "constructor_id")val constructorId: String,
  @Column(name = "season_id")val seasonId: String,
  @Column(name = "theoretical_performance")val theoreticalPerformance: Float,
  @Column(name = "is_season_analyzed") val isSeasonAnalyzed: Boolean
)
