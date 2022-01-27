package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class EmployeeListDataClass(
    val `data`: List<EmployeeEntity>,
    val message: String,
    val success: Boolean
): Serializable