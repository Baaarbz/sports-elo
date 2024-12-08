package com.barbzdev.f1elo.infrastructure.spring.repository.jpa.season

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeasonDatasource : JpaRepository<SeasonEntity, String> {
  fun findByYear(year: Int): SeasonEntity?
}

@Entity @Table(name = "seasons") data class SeasonEntity(@Id val id: String, val year: Int, val infoUrl: String)
