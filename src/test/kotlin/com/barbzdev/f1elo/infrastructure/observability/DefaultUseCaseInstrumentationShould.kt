package com.barbzdev.f1elo.infrastructure.observability

import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.barbzdev.f1elo.domain.observability.MetricClient
import com.barbzdev.f1elo.domain.observability.MetricEnum.USE_CASE_ELAPSED_TIME
import com.barbzdev.f1elo.domain.observability.MetricEnum.USE_CASE_FAILURE
import com.barbzdev.f1elo.domain.observability.MetricEnum.USE_CASE_SUCCESS
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration
import org.junit.jupiter.api.Test
import java.time.Duration as JavaDuration
import org.assertj.core.api.Assertions.catchThrowable


class DefaultUseCaseInstrumentationShould {
  private val metricClient: MetricClient = mockk(relaxed = true)
  private val instrumentation = DefaultUseCaseInstrumentation(metricClient)

  @Test
  fun `execute the use case and increment and return the result`() {
    val instrumentedUseCase = SuccessUseCase(instrumentation)
    val executeTime = 300.milliseconds

    val result = instrumentedUseCase.execute(executeTime)

    assertThat(result).isEqualTo(SuccessUseCase.SuccessResponse)
    val durationSlot = slot<JavaDuration>()
    verify {
      metricClient.increment(USE_CASE_SUCCESS, mapOf("use_case" to "success", "response" to "SuccessResponse"))
      metricClient.recordTime(USE_CASE_ELAPSED_TIME, capture(durationSlot), mapOf("use_case" to "success"))
    }
    assertThat(durationSlot.captured).isBetween(executeTime.toJavaDuration(), (executeTime + 100.milliseconds).toJavaDuration())
  }

  @Test
  fun `execute failure use case when use case throws exception`() {
    val instrumentedUseCase = FailureUseCase(instrumentation)

    val result = catchThrowable { instrumentedUseCase.execute() }

    assertThat(result).isInstanceOf(TestableDomainException::class)
    verify {
      metricClient.increment(USE_CASE_FAILURE, mapOf("use_case" to "failure", "exception" to "TestableDomainException"))
    }
  }
}

class SuccessUseCase(
  private val instrumentation: UseCaseInstrumentation
) {
  object SuccessResponse

  fun execute(executionTime: Duration = 0.milliseconds) = instrumentation {
    Thread.sleep(executionTime.inWholeMilliseconds)
    SuccessResponse
  }
}

class FailureUseCase(
  private val instrumentation: UseCaseInstrumentation
) {
  object FailureResponse

  fun execute(): FailureResponse = instrumentation {
    throw TestableDomainException
  }
}

object TestableDomainException : RuntimeException("something went wrong") {
  private fun readResolve(): Any = TestableDomainException
}
