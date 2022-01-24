package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class CompanyInfo(
    val address: Any,
    val campaign_email: String,
    val created_at: String,
    val created_by: Int,
    val credits: Int,
    val email: Any,
    val fax: Any,
    val id: Int,
    val industry_id: Int,
    val industry_name: String,
    val name: String,
    val phone_number: String,
    val updated_at: String,
    val updated_by: Int,
    val website: String
): Serializable