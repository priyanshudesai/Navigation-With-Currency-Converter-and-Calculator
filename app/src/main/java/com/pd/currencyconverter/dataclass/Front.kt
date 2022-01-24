package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class Front(
    val medium: String,
    val original: String,
    val small: String
): Serializable