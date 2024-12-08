package com.barbzdev.f1elo.infrastructure.spring.repository.jpa.race

import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.constructor.ConstructorEntity
import com.barbzdev.f1elo.infrastructure.spring.repository.jpa.driver.DriverEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRaceResultDatasource : JpaRepository<RaceResultEntity, String> {
  fun findAllByRace(race: RaceEntity): List<RaceResultEntity>
}

@Entity
@Table(name = "race_results")
data class RaceResultEntity(
  @Id val id: String,
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE]) @JoinColumn(name = "race_id") val race: RaceEntity,
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  @JoinColumn(name = "driver_id")
  val driver: DriverEntity,
  val position: Int,
  val number: String,
  val points: Float,
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  @JoinColumn(name = "constructor_id")
  val constructor: ConstructorEntity,
  val grid: Int,
  val laps: Int,
  val status: String,
  @Column(name = "time_in_millis") val timeInMillis: Long?,
  @Column(name = "fastest_lap_in_millis") val fastestLapInMillis: Long?,
  @Column(name = "average_speed") val averageSpeed: Float?,
  @Column(name = "average_speed_unit") val averageSpeedUnit: String?
)
