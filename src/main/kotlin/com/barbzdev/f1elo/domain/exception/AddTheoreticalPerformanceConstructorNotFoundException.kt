package com.barbzdev.f1elo.domain.exception

data class AddTheoreticalPerformanceConstructorNotFoundException(val constructorId: String) : RuntimeException("Constructor with id $constructorId not found")
