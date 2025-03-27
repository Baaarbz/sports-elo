package com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.mapper

import com.barbzdev.sportselo.core.domain.util.EntityMapper
import com.barbzdev.sportselo.core.domain.valueobject.Elo
import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn
import com.barbzdev.sportselo.formulaone.domain.Driver
import com.barbzdev.sportselo.formulaone.domain.valueobject.driver.Nationality
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEloHistoryEntity
import com.barbzdev.sportselo.formulaone.infrastructure.framework.repository.jpa.driver.DriverEntity
import java.util.UUID

class DriverMapper : EntityMapper<Driver, DriverEntity> {

  override fun toEntity(domain: Driver): DriverEntity {
    val highestElo = domain.highestElo()
    val lowestElo = domain.lowestElo()

    val driverEntity =
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
        currentEloOccurredOn = domain.currentElo().occurredOn.toLocalDate(),
        highestElo = highestElo.value,
        highestEloOccurredOn = highestElo.occurredOn.toLocalDate(),
        lowestElo = lowestElo.value,
        lowestEloOccurredOn = lowestElo.occurredOn.toLocalDate(),
        eloHistory = emptyList())

    val eloHistoryEntities =
      domain.eloRecord().map {
        DriverEloHistoryEntity(
          id = UUID.randomUUID().toString(),
          driver = driverEntity,
          elo = it.value,
          occurredOn = it.occurredOn.toLocalDate())
      }

    return driverEntity.copy(eloHistory = eloHistoryEntities)
  }

  override fun toDomain(entity: DriverEntity): Driver {
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
      eloRecord = entity.eloHistory.map { Elo(value = it.elo, occurredOn = OccurredOn.from(it.occurredOn)) },
    )
  }
}
