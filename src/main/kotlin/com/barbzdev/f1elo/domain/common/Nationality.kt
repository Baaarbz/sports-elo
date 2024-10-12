package com.barbzdev.f1elo.domain.common

enum class Nationality(val countryCode: String, val countryName: String) {
  DUTCH("NL", "Netherlands"),
  AUSTRIAN("AT", "Austria"),
  MEXICAN("MX", "Mexico"),
  ITALIAN("IT", "Italy"),
  SPANISH("ES", "Spain"),
  MONEGASQUE("MC", "Monaco"),
  GERMAN("DE", "Germany"),
  BRITISH("GB", "United Kingdom"),
  AUSTRALIAN("AU", "Australia"),
  CANADIAN("CA", "Canada"),
  SWISS("CH", "Switzerland"),
  CHINESE("CN", "China"),
  DANISH("DK", "Denmark"),
  AMERICAN("US", "United States"),
  JAPANESE("JP", "Japan"),
  THAI("TH", "Thailand"),
  FRENCH("FR", "France"),
  FINNISH("FI", "Finland"),
  SWEDISH("SE", "Sweden"),
  ARGENTINE("AR", "Argentina"),
  BRAZILIAN("BR", "Brazil"),
  SOUTH_AFRICAN("ZA", "South Africa"),
  HONG_KONG("HK", "Hong Kong"),
  URUGUAYAN("UY", "Uruguay"),
  COLOMBIAN("CO", "Colombia"),
  PORTUGUESE("PT", "Portugal"),
  RUSSIAN("RU", "Russia"),
  IRISH("IE", "Ireland"),
  BELGIAN("BE", "Belgium"),
  NORWEGIAN("NO", "Norway"),
  POLISH("PL", "Poland"),
  CZECH("CZ", "Czech Republic"),
  HUNGARIAN("HU", "Hungary"),
  GREEK("GR", "Greece"),
  ROMANIAN("RO", "Romania"),
  BULGARIAN("BG", "Bulgaria"),
  CROATIAN("HR", "Croatia"),
  SLOVAK("SK", "Slovakia"),
  SLOVENIAN("SI", "Slovenia"),
  CHILEAN("CL", "Chile"),
  PERUVIAN("PE", "Peru"),
  VENEZUELAN("VE", "Venezuela"),
  BOLIVIAN("BO", "Bolivia"),
  ECUADORIAN("EC", "Ecuador"),
  PARAGUAYAN("PY", "Paraguay"),
  COSTA_RICAN("CR", "Costa Rica"),
  PANAMANIAN("PA", "Panama"),
  CUBAN("CU", "Cuba"),
  DOMINICAN("DO", "Dominican Republic"),
  GUATEMALAN("GT", "Guatemala"),
  HONDURAN("HN", "Honduras"),
  SALVADORAN("SV", "El Salvador"),
  NICARAGUAN("NI", "Nicaragua"),
  JAMAICAN("JM", "Jamaica"),
  TRINIDADIAN("TT", "Trinidad and Tobago"),
  BARBADIAN("BB", "Barbados"),
  BAHAMIAN("BS", "Bahamas"),
  BELIZEAN("BZ", "Belize"),
  GUYANESE("GY", "Guyana"),
  SURINAMESE("SR", "Suriname"),
  INDIAN("IN", "India"),
  PAKISTANI("PK", "Pakistan"),
  BANGLADESHI("BD", "Bangladesh"),
  SRI_LANKAN("LK", "Sri Lanka"),
  NEPALESE("NP", "Nepal"),
  MALDIVIAN("MV", "Maldives"),
  MALAYSIAN("MY", "Malaysia"),
  SINGAPOREAN("SG", "Singapore"),
  INDONESIAN("ID", "Indonesia"),
  FILIPINO("PH", "Philippines"),
  VIETNAMESE("VN", "Vietnam"),
  SOUTH_KOREAN("KR", "South Korea"),
  KAZAKH("KZ", "Kazakhstan"),
  UZBEK("UZ", "Uzbekistan"),
  TURKMEN("TM", "Turkmenistan"),
  KYRGYZ("KG", "Kyrgyzstan"),
  TAJIK("TJ", "Tajikistan"),
  MONGOLIAN("MN", "Mongolia"),
  BAHRAINI("BH", "Bahrain"),
  QATARI("QA", "Qatar"),
  OMANI("OM", "Oman"),
  KUWAITI("KW", "Kuwait"),
  EMIRATI("AE", "United Arab Emirates"),
  SAUDI("SA", "Saudi Arabia"),
  ISRAELI("IL", "Israel"),
  IRANIAN("IR", "Iran"),
  IRAQI("IQ", "Iraq"),
  SYRIAN("SY", "Syria"),
  LEBANESE("LB", "Lebanon"),
  JORDANIAN("JO", "Jordan"),
  YEMENI("YE", "Yemen"),
  UNKNOWN("UNKNOWN", "Unknown");

  companion object {
    fun fromGentilic(gentilic: String): Nationality =
      entries
        .firstOrNull { it.name.replace("_", " ").equals(gentilic, ignoreCase = true) }
        ?: UNKNOWN
  }
}
