package com.barbzdev.f1elo.domain.common

data class Elo(val value: Int, val occurredOn: String) : OccurredOn(occurredOn)
