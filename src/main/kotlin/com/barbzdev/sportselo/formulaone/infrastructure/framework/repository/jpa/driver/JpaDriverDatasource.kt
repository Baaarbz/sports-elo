package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
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
    SELECT d
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
    SELECT d
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
  @Column(name = "highest_elo") val highestElo: Int,
  @Column(name = "highest_elo_occurred_on") val highestEloOccurredOn: LocalDate,
  @Column(name = "lowest_elo") val lowestElo: Int,
  @Column(name = "lowest_elo_occurred_on") val lowestEloOccurredOn: LocalDate,
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "driver", cascade = [CascadeType.ALL], orphanRemoval = true)
  val eloHistory: List<DriverEloHistoryEntity>,
) {
  override fun toString(): String {
    return "DriverEntity(id='$id', givenName='$givenName', familyName='$familyName', " +
      "code=$code, permanentNumber=$permanentNumber, birthDate=$birthDate, " +
      "nationality='$nationality', infoUrl='$infoUrl', currentElo=$currentElo, " +
      "currentEloOccurredOn=$currentEloOccurredOn, highestElo=$highestElo, " +
      "highestEloOccurredOn=$highestEloOccurredOn, lowestElo=$lowestElo, " +
      "lowestEloOccurredOn=$lowestEloOccurredOn, eloHistory=<omitted>)"
  }
}

@Entity
@IdClass(DriverEloHistoryId::class)
@Table(name = "drivers_elo_history")
data class DriverEloHistoryEntity(
  @Id @ManyToOne @JoinColumn(name = "driver_id") val driver: DriverEntity,
  @Id val elo: Int,
  @Id @Column(name = "occurred_on") val occurredOn: LocalDate,
) {
  override fun toString(): String {
    return "DriverEloHistoryEntity(driver.id=${driver.id}, elo=$elo, occurredOn=$occurredOn)"
  }
}

data class DriverEloHistoryId(val driver: String = "", val elo: Int = 0, val occurredOn: LocalDate = LocalDate.now()) :
  Serializable
