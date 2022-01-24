package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class Sales(
    val active: Int,
    val app_user: Int,
    val app_version: String,
    val cardphoto_back: String,
    val cardphoto_front: String,
    val cms_user: Int,
    val company_id: Int,
    val confirmation_code: Any,
    val confirmed: Int,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val designation: String,
    val device_id: String,
    val device_type: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val is_deleted: String,
    val last_login_at: String,
    val last_login_ip: String,
    val last_name: String,
    val password_changed_at: Any,
    val phone: String,
    val remember_token: String,
    val timezone: String,
    val updated_at: String,
    val updated_by: Int,
    val username: Any,
    val uuid: Any
): Serializable