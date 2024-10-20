package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import com.barbzdev.f1elo.infrastructure.jpa.entity.SeasonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeasonDatasource : JpaRepository<SeasonEntity, String>
