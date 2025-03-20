package com.barbzdev.sportselo.infrastructure.spring.repository.jpa.constructor

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface JpaConstructorDatasource : JpaRepository<ConstructorEntity, String>

@Entity
@Table(name = "constructors")
data class ConstructorEntity(
  @Id val id: String,
  val name: String,
  val nationality: String,
  @Column(name = "info_url") val infoUrl: String
)
