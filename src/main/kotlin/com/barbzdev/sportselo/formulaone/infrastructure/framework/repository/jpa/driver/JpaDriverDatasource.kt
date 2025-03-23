package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDate
import java.util.Optional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaDriverDatasource : JpaRepository<DriverEntity, String> {
  @Query(
    """
    SELECT d, MIN(deh.elo) as lowest_elo, MAX(deh.elo) as highest_elo
    FROM DriverEntity d
    LEFT JOIN FETCH d.eloHistory AS deh
    """,
  )
  fun findAllJoinDriverEloHistory(pageable: Pageable): Page<DriverEntity>

  @Query(
    """
    SELECT d
    FROM DriverEntity d
    LEFT JOIN FETCH d.eloHistory AS deh
    """,
  )
  fun findAllJoinDriverEloHistory(): List<DriverEntity>

  @Query(
    """
    SELECT d as highest_elo
    FROM DriverEntity d
    LEFT JOIN FETCH d.eloHistory AS deh
    where d.id = :id
    """,
  )
  fun findByIdJoinDriverEloHistory(id: String): Optional<DriverEntity>
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
  @OneToMany(mappedBy = "driver", cascade = [CascadeType.ALL], orphanRemoval = true)
  val eloHistory: List<DriverEloHistoryEntity>,
)

@Entity
@IdClass(DriverEloHistoryId::class)
@Table(name = "drivers_elo_history")
data class DriverEloHistoryEntity(
  @Id @ManyToOne @JoinColumn(name = "driver_id") val driver: DriverEntity,
  @Id val elo: Int,
  @Id @Column(name = "occurred_on") val occurredOn: LocalDate,
)

data class DriverEloHistoryId(val driver: String = "", val elo: Int = 0, val occurredOn: LocalDate = LocalDate.now()) :
  Serializable
