package com.barbzdev.sportselo.infrastructure.spring.repository.jpa.driver

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
interface JpaDriverIRatingHistoryDatasource : JpaRepository<DriverIRatingHistoryEntity, String> {
  fun findAllByDriver(driver: DriverEntity): List<DriverIRatingHistoryEntity>
}

@Entity
@IdClass(DriverIRatingHistoryId::class)
@Table(name = "drivers_irating_history")
data class DriverIRatingHistoryEntity(
  @Id
  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
  @JoinColumn(name = "driver_id")
  val driver: DriverEntity,
  @Id @Column(name = "irating") val iRating: Int,
  @Id @Column(name = "occurred_on") val occurredOn: LocalDate,
)

data class DriverIRatingHistoryId(
  val driver: String = "",
  val iRating: Int = 0,
  val occurredOn: LocalDate = LocalDate.now()
) : Serializable
