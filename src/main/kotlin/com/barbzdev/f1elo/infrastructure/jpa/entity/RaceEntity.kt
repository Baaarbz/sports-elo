package com.barbzdev.f1elo.infrastructure.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "races")
data class RaceEntity(
  @Id
  val id: UUID,
  val season: Int,
  val round: Int,
  @Column(name = "info_url")
  val infoUrl: String,
  val name: String,
  @ManyToOne
  @JoinColumn(name = "circuit_id")
  val circuit: CircuitEntity,
  @Column(name = "occurred_on")
  val occurredOn: LocalDate
)
