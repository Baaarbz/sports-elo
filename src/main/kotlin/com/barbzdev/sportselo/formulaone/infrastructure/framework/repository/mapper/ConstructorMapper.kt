package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.season.ConstructorEntity

class ConstructorMapper : EntityMapper<Constructor, ConstructorEntity> {

  override fun toEntity(domain: Constructor): ConstructorEntity =
    ConstructorEntity(
      id = domain.id().value,
      name = domain.name().value,
      nationality = domain.nationality().countryCode,
      infoUrl = domain.infoUrl().value,
    )

  override fun toDomain(entity: ConstructorEntity): Constructor =
    Constructor.create(
      id = entity.id,
      name = entity.name,
      nationality = Nationality.fromCountryCode(entity.nationality),
      infoUrl = entity.infoUrl,
    )
}
