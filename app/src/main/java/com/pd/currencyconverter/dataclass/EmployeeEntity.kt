package com.pd.currencyconverter.dataclass

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.pd.currencyconverter.dataclass.*
import java.io.Serializable

@Entity(tableName = "cardInformation")
data class EmployeeEntity (
    @PrimaryKey(autoGenerate = false)
    val id:Int=0,
    val created_at: String,
    val designation: String,
    val emails: List<Email>,
    val first_name: String,
    val is_decisionmaker: Boolean,
    val last_name: String,
    val phones: List<Phone>,
    val status: String,
    val tags: String,
    val type: String,
    val card: Card,
    val company_info: CompanyInfo,
): Serializable






