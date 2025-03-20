package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race

import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.circuit.CircuitEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.SeasonEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRaceDatasource : JpaRepository<RaceEntity, String> {
  fun findAllBySeason(season: SeasonEntity): List<RaceEntity>
}

@Entity
@Table(name = "races")
data class RaceEntity(
    @Id val id: String,
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  @JoinColumn(name = "season_id")
  var season: SeasonEntity,
    val round: Int,
    @Column(name = "info_url") val infoUrl: String,
    val name: String,
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  @JoinColumn(name = "circuit_id")
  val circuit: CircuitEntity,
    @Column(name = "occurred_on") val occurredOn: LocalDate
)
