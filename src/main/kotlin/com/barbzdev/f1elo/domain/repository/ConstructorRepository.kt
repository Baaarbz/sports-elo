package com.barbzdev.f1elo.domain.repository

import com.barbzdev.f1elo.domain.Constructor
import com.barbzdev.f1elo.domain.ConstructorId

interface ConstructorRepository {
  fun findBy(id: ConstructorId): Constructor?
}
