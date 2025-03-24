package com.barbzdev.sportselo.observability

import com.barbzdev.sportselo.core.domain.observability.UseCaseInstrumentation
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

inline fun <reified InstrumentationClass : UseCaseInstrumentation> instrumentationMock(): InstrumentationClass =
  mock<InstrumentationClass>().apply {
    whenever(invoke(any<() -> Any>())).thenAnswer { invocation -> (invocation.getArgument(0) as () -> Any).invoke() }
  }
