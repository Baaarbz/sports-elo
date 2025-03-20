package com.barbzdev.sportselo.domain.observability

import kotlin.reflect.KClass

object UseCaseNameExtractor {
  private const val UNKNOWN = "unknown"
  private val suffixesToRemove = listOf("Service", "UseCase")

  fun takeUseCaseNameFromEmbeddingClass(clazz: KClass<out Any>) =
    clazz.java.name.removeLambdaReference()?.extractClassName()?.removeUseCaseSuffixes().toSnakeCase() ?: UNKNOWN

  private fun String.removeLambdaReference() = split("$").firstOrNull()

  private fun String.extractClassName() = split(".").lastOrNull()

  private fun String.removeUseCaseSuffixes() = suffixesToRemove.fold(this) { acc, suffix -> acc.removeSuffix(suffix) }

  private fun String?.toSnakeCase() = this?.replace(Regex("(\\p{Lower})(\\p{Upper}+)"), "$1_$2")?.lowercase()
}
