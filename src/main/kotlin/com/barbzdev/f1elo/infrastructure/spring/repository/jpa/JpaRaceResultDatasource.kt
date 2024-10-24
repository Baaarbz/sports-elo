package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRaceResultDatasource : JpaRepository<RaceResultEntity, String>

@Entity
@Table(name = "race_results")
data class RaceResultEntity(
  @Id
  val id: String,
  @ManyToOne
  @JoinColumn(name = "race_id")
  val race: RaceEntity,
  @ManyToOne
  @JoinColumn(name = "driver_id")
  val driver: DriverEntity,
  val position: Int,
  val points: Float,
  @ManyToOne
  @JoinColumn(name = "constructor_id")
  val constructor: ConstructorEntity,
  val grid: Int,
  val laps: Int,
  val status: String,
  @Column(name = "time_in_millis")
  val timeInMillis: Long,
  @Column(name = "fastest_lap_in_millis")
  val fastestLapInMillis: Long?,
  @Column(name = "average_speed")
  val averageSpeed: Float?,
  @Column(name = "average_speed_unit")
  val averageSpeedUnit: String?
)
