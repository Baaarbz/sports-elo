package com.barbzdev.f1elo.infrastructure.spring.event

sealed class RatingReprocessingEvent

data object EloReprocessingEvent : RatingReprocessingEvent()

data object IRatingReprocessingEvent : RatingReprocessingEvent()
