package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface JpaCircuitDatasource : JpaRepository<CircuitEntity, String>

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
