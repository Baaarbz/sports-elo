package com.barbzdev.f1elo.infrastructure.spring.repository.jpa

import com.barbzdev.f1elo.infrastructure.jpa.entity.ConstructorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaConstructorDatasource : JpaRepository<ConstructorEntity, String>
