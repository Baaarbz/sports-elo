package com.barbzdev.sportselo.formulaone.infrastructure.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEntity


class DriverMapper(
  private val eloHistoryDatasource: JpaDriverEloHistoryDatasource
) : EntityMapper<Driver, DriverEntity> {

  override fun toEntity(domain: Driver): DriverEntity =
    DriverEntity(
      id = domain.id().value,
      familyName = domain.fullName().familyName,
      givenName = domain.fullName().givenName,
      code = domain.code()?.value,
      permanentNumber = domain.permanentNumber()?.value,
      birthDate = domain.birthDate().date.toLocalDate(),
      nationality = domain.nationality().countryCode,
      infoUrl = domain.infoUrl().value,
      currentElo = domain.currentElo().value,
      currentEloOccurredOn = domain.currentElo().occurredOn.toLocalDate()
    )

  override fun toDomain(entity: DriverEntity): Driver {
    val eloRecordEntity = eloHistoryDatasource.findAllByDriver(entity)

    return Driver.create(
      id = entity.id,
      familyName = entity.familyName,
      givenName = entity.givenName,
      code = entity.code,
      permanentNumber = entity.permanentNumber,
      birthDate = entity.birthDate.toString(),
      nationality = Nationality.fromCountryCode(entity.nationality),
      infoUrl = entity.infoUrl,
      currentElo = entity.currentElo,
      currentEloOccurredOn = entity.currentEloOccurredOn.toString(),
      eloRecord = eloRecordEntity.map { Elo(value = it.elo, occurredOn = OccurredOn.from(it.occurredOn)) },
    )
  }
}
