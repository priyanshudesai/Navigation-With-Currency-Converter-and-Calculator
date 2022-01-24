package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class Data(
    val bucket_requested_by: Any,
    val bucket_status: Int,
    val card: Card,
    val company: Company,
    val company_id: Int,
    val company_info: CompanyInfo,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val designation: String,
    val emails: List<Email>,
    val external_sales_id: Any,
    val first_name: String,
    val id: Int,
    val is_decisionmaker: Boolean,
    val is_deleted: String,
    val is_edit_card_photo: Boolean,
    val is_external: Int,
    val is_favourite: Boolean,
    val is_verified: Boolean,
    val last_name: String,
    val phones: List<Phone>,
    val sales: Sales,
    val sales_id: Int,
    val status: String,
    val tags: String,
    val type: String,
    val updated_at: String,
    val updated_by: Int
): Serializable