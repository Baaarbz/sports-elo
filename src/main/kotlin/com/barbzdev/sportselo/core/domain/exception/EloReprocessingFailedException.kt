package com.barbzdev.sportselo.core.domain.exception

data class EloReprocessingFailedException(val year: Int) :
  RuntimeException("Something has failed while reprocessing the elo of the year $year")
