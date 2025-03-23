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
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaSeasonDatasource : JpaRepository<SeasonEntity, String> {
  @Query("""
    SELECT DISTINCT s
    FROM SeasonEntity s
    LEFT JOIN FETCH s.races r
    LEFT JOIN FETCH r.circuit c
    ORDER BY s.year DESC
    LIMIT 1
  """)
  fun findLastSeasonWithRaces(): SeasonEntity?

  @Query("""
    SELECT DISTINCT r
    FROM RaceEntity r
    LEFT JOIN FETCH r.raceResults rs
    LEFT JOIN FETCH rs.driver d
    LEFT JOIN FETCH rs.constructor co
    WHERE r.season.id = :seasonId
  """)
  fun findRaceResultsBySeasonId(seasonId: String): List<RaceEntity>

  @Query("""
    SELECT DISTINCT s
    FROM SeasonEntity s
    LEFT JOIN FETCH s.races r
    LEFT JOIN FETCH r.circuit c
    WHERE s.year = :year
  """)
  fun findByYearWithRaces(year: Int): SeasonEntity?
}

@Entity
@Table(name = "seasons")
data class SeasonEntity(
  @Id val id: String,
  val year: Int,
  val infoUrl: String,
  @OneToMany(mappedBy = "season", cascade = [CascadeType.ALL], orphanRemoval = true) val races: List<RaceEntity>
) {
  override fun toString(): String {
    return "SeasonEntity(id='$id', year=$year, infoUrl='$infoUrl', races=<omitted>)"
  }
}

@Entity
@Table(name = "races")
data class RaceEntity(
  @Id val id: String,
  @ManyToOne @JoinColumn(name = "season_id") var season: SeasonEntity,
  val round: Int,
  @Column(name = "info_url") val infoUrl: String,
  val name: String,
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE]) @JoinColumn(name = "circuit_id") val circuit: CircuitEntity,
  @Column(name = "occurred_on") val occurredOn: LocalDate,
  @OneToMany(mappedBy = "race", cascade = [CascadeType.ALL], orphanRemoval = true)
  val raceResults: List<RaceResultEntity>,
) {
  override fun toString(): String {
    return "RaceEntity(id='$id', season=${season.year}, round=$round, infoUrl='$infoUrl', name='$name', circuit=$circuit, occurredOn=$occurredOn, raceResults=<omitted>)"
  }
}

@Entity
@Table(name = "race_results")
data class RaceResultEntity(
  @Id val id: String,
  @ManyToOne @JoinColumn(name = "race_id") val race: RaceEntity,
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE]) @JoinColumn(name = "driver_id") val driver: DriverEntity,
  val position: Int,
  val number: String,
  val points: Float,
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE]) @JoinColumn(name = "constructor_id") val constructor: ConstructorEntity,
  val grid: Int,
  val laps: Int,
  val status: String,
  @Column(name = "time_in_millis") val timeInMillis: Long?,
  @Column(name = "fastest_lap_in_millis") val fastestLapInMillis: Long?,
  @Column(name = "average_speed") val averageSpeed: Float?,
  @Column(name = "average_speed_unit") val averageSpeedUnit: String?
) {
  override fun toString(): String {
    return "RaceResultEntity(id='$id', race=${race.id}, driver=$driver, position=$position, number='$number', points=$points, constructor=$constructor, grid=$grid, laps=$laps, status='$status', timeInMillis=$timeInMillis, fastestLapInMillis=$fastestLapInMillis, averageSpeed=$averageSpeed, averageSpeedUnit=$averageSpeedUnit)"
  }
}

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
