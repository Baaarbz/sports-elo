package com.barbzdev.f1elo.infrastructure.observability

import com.barbzdev.f1elo.domain.observability.UseCaseInstrumentation
import kotlin.reflect.KClass
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

open class DefaultUseCaseInstrumentation : UseCaseInstrumentation {

  private val logger by LoggerDelegate()

  override fun <Response> invoke(useCase: () -> Response): Response {
    val useCaseName = takeUseCaseNameFromEmbeddingClass(useCase::class)

    return measureTimedValue { execute(useCase) }
      .logExecutionTime(useCaseName)
      .onFailure { exception -> onFailedUseCaseExecution(exception, useCaseName) }
      .onSuccess { onSuccessUseCaseExecution(useCaseName) }
      .getOrThrow()
  }

  private fun <Response> execute(func: () -> Response): Result<Response> = runCatching { func() }

  private fun <Response> TimedValue<Response>.logExecutionTime(useCaseName: String): Response {
    logger.info("use case::$useCaseName executed in ${duration.inWholeMilliseconds}ms")
    return value
  }

  private fun onFailedUseCaseExecution(throwable: Throwable, useCaseName: String) {
    logger.error(
      "Exception [${throwable::class.simpleName}] executing use case $useCaseName reason: ${throwable.message}",
      throwable)
  }

  private fun onSuccessUseCaseExecution(useCaseName: String) {
    logger.info("use case::$useCaseName executed successfully")
  }

  private companion object {
    const val UNKNOWN = "unknown"
    val suffixesToRemove = listOf("Service", "UseCase")

    fun takeUseCaseNameFromEmbeddingClass(clazz: KClass<out Any>) =
      clazz.java.name.removeLambdaReference()?.extractClassName()?.removeUseCaseSuffixes().toSnakeCase() ?: UNKNOWN

    fun String.removeLambdaReference() = split("$").firstOrNull()

    fun String.extractClassName() = split(".").lastOrNull()

    fun String.removeUseCaseSuffixes() = suffixesToRemove.fold(this) { acc, suffix -> acc.removeSuffix(suffix) }

    fun String?.toSnakeCase() = this?.replace(Regex("(\\p{Lower})(\\p{Upper}+)"), "$1_$2")?.lowercase()
  }
}
