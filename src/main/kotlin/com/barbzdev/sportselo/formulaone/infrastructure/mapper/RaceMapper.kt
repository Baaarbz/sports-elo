package com.barbzdev.sportselo.formulaone.infrastructure.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.RaceEntity

class RaceMapper(
  private val circuitMapper: CircuitMapper,
  private val seasonMapper: SeasonMapper
): EntityMapper<Race, RaceEntity> {

  override fun toEntity(domain: Race): RaceEntity {
    return RaceEntity(
      id = domain.id().value,
      season = seasonMapper.toEntity(domain.season().value),
      round = domain.round().value,
      circuit = circuitMapper.toEntity(domain.circuit()),
      infoUrl = domain.infoUrl().value,
      occurredOn = domain.occurredOn().date.toLocalDate(),
      name = domain.name().value
    )
  }

  override fun toDomain(entity: RaceEntity): Race {
    return Race.create(
      id = entity.id,
      round = entity.round,
      circuit = circuitMapper.toDomain(entity.circuit),
      infoUrl = entity.infoUrl,
      occurredOn = entity.occurredOn.toString(),
      name = entity.name,
      results = entity.
    )
  }
}
