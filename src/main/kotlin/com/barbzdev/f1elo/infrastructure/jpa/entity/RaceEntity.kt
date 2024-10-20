package com.barbzdev.f1elo.infrastructure.jpa.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "races")
data class RaceEntity(
  @Id
  val id: String,
  @ManyToOne
  @JoinColumn(name = "season_id")
  private val season: SeasonEntity,
  val round: Int,
  @Column(name = "info_url")
  val infoUrl: String,
  val name: String,
  @ManyToOne
  @JoinColumn(name = "circuit_id")
  val circuit: CircuitEntity,
  @OneToMany(mappedBy = "race", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
  private val results: List<RaceResultEntity>,
  @Column(name = "occurred_on")
  val occurredOn: LocalDate
)
