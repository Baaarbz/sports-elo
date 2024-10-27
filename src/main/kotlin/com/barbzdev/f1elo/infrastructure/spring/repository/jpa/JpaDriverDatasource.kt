package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface JpaDriverDatasource : JpaRepository<DriverEntity, String>

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
  @Column(name = "current_elo_occurred_on") val currentEloOccurredOn: LocalDate
)
