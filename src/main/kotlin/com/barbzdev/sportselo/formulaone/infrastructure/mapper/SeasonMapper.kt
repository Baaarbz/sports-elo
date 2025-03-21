package com.barbzdev.sportselo.formulaone.infrastructure.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.Race
import com.barbzdev.sportselo.formulaone.domain.Season
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.race.RaceEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.SeasonEntity
import com.barbzdev.sportselo.formulaone.infrastructure.mapper.DomainToEntityMapper.toEntity

class SeasonMapper : EntityMapper<Season, SeasonEntity> {

  override fun toEntity(domain: Season): SeasonEntity {
    return SeasonEntity(
      id = domain.id().value,
      year = domain.year().value,
      infoUrl = domain.infoUrl().value
    )
  }

  override fun toDomain(entity: SeasonEntity): Season {
    return Season.create(
      id = entity.id,
      year = entity.year,
      infoUrl = entity.infoUrl
    )
  }
}
