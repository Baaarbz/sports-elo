package com.barbzdev.f1elo.infrastructure.spring.repository.jpa.driver

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaDriverDatasource : JpaRepository<DriverEntity, String> {
  @Query(
    """
    SELECT d.*, MIN(deh.elo) as lowest_elo, MAX(deh.elo) as highest_elo
    FROM drivers d
    JOIN drivers_elo_history deh ON d.id = deh.driver_id
    GROUP BY d.id
    """,
    nativeQuery = true)
  fun findAllJoinDriverEloHistory(pageable: Pageable): Page<DriverEntity>

  @Query(
    """
    SELECT d.*, MIN(dirh.irating) as lowest_irating, MAX(dirh.irating) as highest_irating
    FROM drivers d
    JOIN drivers_irating_history dirh ON d.id = dirh.driver_id
    GROUP BY d.id
    """,
    nativeQuery = true)
  fun findAllJoinDriverIRatingHistory(pageable: Pageable): Page<DriverEntity>
}

@Entity
@Table(name = "drivers")
data class DriverEntity(
  @Id val id: String,
  @Column(name = "given_name") val givenName: String,
  @Column(name = "family_name") val familyName: String,
  val code: String?,
  @Column(name = "permanent_number") val permanentNumber: String?,
  @Column(name = "birth_date") val birthDate: LocalDate,
  val nationality: String,
  @Column(name = "info_url") val infoUrl: String,
  @Column(name = "current_elo") val currentElo: Int,
  @Column(name = "current_elo_occurred_on") val currentEloOccurredOn: LocalDate,
  @Column(name = "current_irating") val currentIRating: Int,
  @Column(name = "current_irating_occurred_on") val currentIRatingOccurredOn: LocalDate
)
