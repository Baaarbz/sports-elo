package com.barbzdev.sportselo.infrastructure.spring.event

sealed class RatingReprocessingEvent

data object EloReprocessingEvent : RatingReprocessingEvent()

data object IRatingReprocessingEvent : RatingReprocessingEvent()
