package com.barbzdev.f1elo.domain.common

data class Elo(val rating: Int, val occurredOn: String): OccurredOn(occurredOn)
