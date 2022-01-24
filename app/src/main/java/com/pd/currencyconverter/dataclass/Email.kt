package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class Email(
    val email: String,
    val type: String
): Serializable