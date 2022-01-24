package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class EmployeeListDataClass(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Serializable