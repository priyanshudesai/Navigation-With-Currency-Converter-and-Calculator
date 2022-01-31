package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class Front(
    val original: String,
    val small: String
) : Serializable