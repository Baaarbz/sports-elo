package com.barbzdev.f1elo.infrastructure.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "circuits")
data class CircuitEntity(
  @Id
  val id: String,
  val name: String,
  val latitude: String,
  val longitude: String,
  val country: String,
  val locality: String,
  @Column(name = "info_url")
  val infoUrl: String
)
