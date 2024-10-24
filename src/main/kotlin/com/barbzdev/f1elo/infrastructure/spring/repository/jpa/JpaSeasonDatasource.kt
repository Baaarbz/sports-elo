package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeasonDatasource : JpaRepository<SeasonEntity, String>

@Entity
@Table(name = "seasons")
data class SeasonEntity(
  @Id
  val id: String,
  val year: Int,
  val infoUrl: String,
  @OneToMany(mappedBy = "season", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
  val races: List<RaceEntity>
)
