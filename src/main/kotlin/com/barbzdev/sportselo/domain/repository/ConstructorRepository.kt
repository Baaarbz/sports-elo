package com.barbzdev.sportselo.domain.repository

import com.barbzdev.sportselo.domain.Constructor
import com.barbzdev.sportselo.domain.ConstructorId

interface ConstructorRepository {
  fun findBy(id: ConstructorId): Constructor?
}
