package com.barbzdev.sportselo.formulaone.domain.repository

import com.barbzdev.sportselo.formulaone.domain.Constructor
import com.barbzdev.sportselo.formulaone.domain.ConstructorId

interface ConstructorRepository {
  fun findBy(id: ConstructorId): Constructor?
}
