package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season

import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeasonDatasource : JpaRepository<SeasonEntity, String> {
  fun findByYear(year: Int): SeasonEntity?
}

@Entity
@Table(name = "seasons")
data class SeasonEntity(
  @Id val id: String,
  val year: Int,
  val infoUrl: String,
  @OneToMany(mappedBy = "season", cascade = [CascadeType.ALL], orphanRemoval = true) val races: List<RaceEntity>
)

@Entity
@Table(name = "races")
data class RaceEntity(
  @Id val id: String,
  @ManyToOne @JoinColumn(name = "season_id") var season: SeasonEntity,
  val round: Int,
  @Column(name = "info_url") val infoUrl: String,
  val name: String,
  @ManyToOne @JoinColumn(name = "circuit_id") val circuit: CircuitEntity,
  @Column(name = "occurred_on") val occurredOn: LocalDate,
  @OneToMany(mappedBy = "race", cascade = [CascadeType.ALL], orphanRemoval = true)
  val raceResults: List<RaceResultEntity>
)

@Entity
@Table(name = "race_results")
data class RaceResultEntity(
  @Id val id: String,
  @ManyToOne @JoinColumn(name = "race_id") val race: RaceEntity,
  @ManyToOne @JoinColumn(name = "driver_id") val driver: DriverEntity,
  val position: Int,
  val number: String,
  val points: Float,
  @ManyToOne @JoinColumn(name = "constructor_id") val constructor: ConstructorEntity,
  val grid: Int,
  val laps: Int,
  val status: String,
  @Column(name = "time_in_millis") val timeInMillis: Long?,
  @Column(name = "fastest_lap_in_millis") val fastestLapInMillis: Long?,
  @Column(name = "average_speed") val averageSpeed: Float?,
  @Column(name = "average_speed_unit") val averageSpeedUnit: String?
)

@Entity
@Table(name = "constructors")
data class ConstructorEntity(
  @Id val id: String,
  val name: String,
  val nationality: String,
  @Column(name = "info_url") val infoUrl: String
)

@Entity
@Table(name = "circuits")
data class CircuitEntity(
  @Id val id: String,
  val name: String,
  val latitude: String,
  val longitude: String,
  val country: String,
  val locality: String,
  @Column(name = "info_url") val infoUrl: String
)
