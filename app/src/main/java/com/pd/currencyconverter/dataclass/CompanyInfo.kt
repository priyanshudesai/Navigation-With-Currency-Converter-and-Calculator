package com.pd.currencyconverter.dataclass

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompanyInfo(
    val address: Any,
    @SerializedName("campaign_email")
    @ColumnInfo(name = "campaign_email")
    val campaignEmail: String,
    val email: Any,
    @SerializedName("industry_name")
    @ColumnInfo(name = "industry_name")
    val industryName: String,
    val name: String,
    @SerializedName("phone_number")
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    val website: String
) : Serializable