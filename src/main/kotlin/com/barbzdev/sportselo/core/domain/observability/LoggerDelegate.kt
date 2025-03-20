package com.barbzdev.sportselo.core.domain.observability

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerDelegate : ReadOnlyProperty<Any?, Logger> {

  private var logger: Logger? = null

  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Logger =
    logger ?: createLogger(thisRef!!.javaClass)

  private companion object {
    fun <T> createLogger(clazz: Class<T>): Logger = LoggerFactory.getLogger(clazz)
  }
}
