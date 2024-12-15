package com.barbzdev.f1elo.domain.exception

data class IRatingReprocessingFailedException(val year: Int) :
  RuntimeException("Something has failed while reprocessing the iRating of the year $year")
