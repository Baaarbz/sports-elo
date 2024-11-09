package com.barbzdev.f1elo.infrastructure.observability

import com.barbzdev.f1elo.domain.observability.MetricClient
import com.barbzdev.f1elo.domain.observability.MetricEnum.USE_CASE_ELAPSED_TIME
import com.barbzdev.f1elo.domain.observability.MetricEnum.USE_CASE_FAILURE
import com.barbzdev.f1elo.domain.observability.MetricEnum.USE_CASE_SUCCESS
import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import com.barbzdev.f1elo.domain.observability.UseCaseNameExtractor.takeUseCaseNameFromEmbeddingClass
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue
import kotlin.time.toJavaDuration

open class DefaultUseCaseInstrumentation(private val metricClient: MetricClient) : UseCaseInstrumentation {

  private val logger by LoggerDelegate()

  override fun <Response> invoke(useCase: () -> Response): Response {
    val useCaseName = takeUseCaseNameFromEmbeddingClass(useCase::class)

    return measureTimedValue { execute(useCase) }
      .metricUseCasElapsedTime(useCaseName)
      .onFailure { exception -> onFailedUseCaseExecution(exception, useCaseName) }
      .onSuccess { onSuccessUseCaseExecution(useCaseName, it) }
      .getOrThrow()
  }

  private fun <Response> execute(func: () -> Response): Result<Response> = runCatching { func() }

  private fun <Response> TimedValue<Response>.metricUseCasElapsedTime(useCaseName: String): Response {
    metricClient.recordTime(
      USE_CASE_ELAPSED_TIME,
      duration.toJavaDuration(),
      mapOf(USE_CASE_TAG to useCaseName),
    )
    return value
  }

  private fun onFailedUseCaseExecution(throwable: Throwable, useCaseName: String) {
    logger.error(
      "Exception [${throwable::class.simpleName}] executing use case $useCaseName reason: ${throwable.message}",
      throwable,
    )

    metricClient.increment(
      USE_CASE_FAILURE,
      mapOf(
        USE_CASE_TAG to useCaseName,
        EXCEPTION_TAG to (throwable::class.simpleName ?: UNKNOWN),
      ),
    )
  }

  private fun <Response> onSuccessUseCaseExecution(useCaseName: String, response: Response) {
    metricClient.increment(
      USE_CASE_SUCCESS,
      mapOf(
        USE_CASE_TAG to useCaseName,
        RESPONSE_TAG to (response?.let { it::class.simpleName } ?: UNKNOWN),
      ),
    )
  }

  private companion object {
    const val USE_CASE_TAG = "use_case"
    const val EXCEPTION_TAG = "exception"
    const val RESPONSE_TAG = "response"
    const val UNKNOWN = "unknown"
  }
}
