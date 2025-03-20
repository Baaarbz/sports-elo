package com.barbzdev.sportselo.core.domain.valueobject

data class Elo(val value: Int, val occurredOn: String) : OccurredOn(occurredOn)
