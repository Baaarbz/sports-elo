package com.barbzdev.sportselo.formulaone.infrastructure.framework.event

sealed class RatingReprocessingEvent

data object EloReprocessingEvent : RatingReprocessingEvent()

data object IRatingReprocessingEvent : RatingReprocessingEvent()
