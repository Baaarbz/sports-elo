package com.barbzdev.sportselo.domain.exception

data class AddTheoreticalPerformanceConstructorNotFoundException(val constructorId: String) :
  RuntimeException("Constructor with id $constructorId not found")
