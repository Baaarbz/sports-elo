package com.barbzdev.f1elo.infrastructure.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "constructors")
data class ConstructorEntity(
  @Id
  val id: String,
  val name: String,
  val nationality: String,
  @Column(name = "info_url")
  val infoUrl: String
)
