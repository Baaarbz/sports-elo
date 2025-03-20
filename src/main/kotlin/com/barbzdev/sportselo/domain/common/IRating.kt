package com.barbzdev.sportselo.domain.common

import com.barbzdev.sportselo.core.domain.valueobject.OccurredOn

data class IRating(val value: Int, val occurredOn: String) : OccurredOn(occurredOn)
