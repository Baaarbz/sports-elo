package com.barbzdev.f1elo.domain.common

data class IRating(val value: Int, val occurredOn: String) : OccurredOn(occurredOn)
