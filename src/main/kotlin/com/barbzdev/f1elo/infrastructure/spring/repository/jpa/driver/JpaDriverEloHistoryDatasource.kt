package com.barbzdev.f1elo.infrastructure.spring.repository.jpa.driver

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaDriverEloHistoryDatasource : JpaRepository<DriverEloHistoryEntity, String> {
  fun findAllByDriver(driver: DriverEntity): List<DriverEloHistoryEntity>
}

@Entity
@IdClass(DriverEloHistoryId::class)
@Table(name = "drivers_elo_history")
data class DriverEloHistoryEntity(
  @Id
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  @JoinColumn(name = "driver_id")
  val driver: DriverEntity,
  @Id val elo: Int,
  @Id @Column(name = "occurred_on") val occurredOn: LocalDate,
)

data class DriverEloHistoryId(val driver: String = "", val elo: Int = 0, val occurredOn: LocalDate = LocalDate.now()) :
  Serializable
