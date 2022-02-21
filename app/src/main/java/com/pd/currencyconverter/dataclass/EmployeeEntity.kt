package com.pd.currencyconverter.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.pd.currencyconverter.utils.ConstantUtils
import java.io.Serializable

@Entity(tableName = ConstantUtils.TABLE_NAME_CARD_INFORMATION)
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    val designation: String,
    val emails: List<Email>,
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @SerializedName("is_decisionmaker")
    @ColumnInfo(name = "is_decisionmaker")
    val isDecisionMaker: Boolean,
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val phones: List<Phone>,
    val status: String,
    val tags: String,
    val type: String,
    val card: Card,
    @SerializedName("company_info")
    @ColumnInfo(name = "company_info")
    val companyInfo: CompanyInfo,
    val age: String?
) : Serializable






