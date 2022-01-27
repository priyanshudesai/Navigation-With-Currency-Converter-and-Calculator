package com.pd.currencyconverter.dataclass

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.pd.currencyconverter.dataclass.*
import java.io.Serializable

@Entity(tableName = "cardInformation")
data class EmployeeEntity (
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false) 
    @ColumnInfo(name = "id") 
    val id:Int=0,

    @ColumnInfo(name = "created_at")
    val created_at: String,
    @ColumnInfo(name = "designation")
    val designation: String,

    @ColumnInfo(name = "emails")
    val emails: List<Email>,
    @ColumnInfo(name = "first_name")
    val first_name: String,
    @ColumnInfo(name = "is_decisionmaker")
    val is_decisionmaker: Boolean,
    @ColumnInfo(name = "last_name")
    val last_name: String,
    @ColumnInfo(name = "phones")
    val phones: List<Phone>,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "tags")
    val tags: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "card")
    val card: Card,
    @ColumnInfo(name = "company_info")
    val company_info: CompanyInfo,

): Serializable






