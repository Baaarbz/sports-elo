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
@Table(name = "drivers_elo_history")
data class DriverEloHistoryEntity(
  @Id
  val id: UUID,
  @ManyToOne
  @JoinColumn(name = "driver_id")
  val driver: DriverEntity,
  val elo: Int,
  @Column(name = "occurred_on")
  val occurredOn: LocalDate,
  @Column(name = "updated_at")
  val updatedAt: LocalDate
)
