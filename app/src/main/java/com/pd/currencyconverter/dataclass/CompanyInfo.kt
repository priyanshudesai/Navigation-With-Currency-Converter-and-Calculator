package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class CompanyInfo(
    val address: Any,
    val campaign_email: String,
    val email: Any,
    val industry_name: String,
    val name: String,
    val phone_number: String,
    val website: String
): Serializable