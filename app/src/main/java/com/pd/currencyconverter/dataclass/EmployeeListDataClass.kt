package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class EmployeeListDataClass(
    val `data`: MutableList<EmployeeEntity>,
    val message: String,
    val success: Boolean
) : Serializable