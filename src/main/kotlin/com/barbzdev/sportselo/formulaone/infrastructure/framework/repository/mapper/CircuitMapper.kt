package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.Circuit
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.CircuitEntity

class CircuitMapper : EntityMapper<Circuit, CircuitEntity> {

  override fun toEntity(domain: Circuit): CircuitEntity {
    return CircuitEntity(
      id = domain.id().value,
      name = domain.name().value,
      latitude = domain.location().latitude,
      longitude = domain.location().longitude,
      country = domain.country().value,
      infoUrl = domain.infoUrl().value,
      locality = domain.locality().value)
  }

  override fun toDomain(entity: CircuitEntity): Circuit {
    return Circuit.create(
      id = entity.id,
      name = entity.name,
      latitude = entity.latitude,
      longitude = entity.longitude,
      country = entity.country,
      infoUrl = entity.infoUrl,
      locality = entity.locality)
  }
}
