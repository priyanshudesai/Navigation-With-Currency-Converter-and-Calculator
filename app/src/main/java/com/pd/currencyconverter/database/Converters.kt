package com.pd.currencyconverter.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pd.currencyconverter.dataclass.Card
import com.pd.currencyconverter.dataclass.CompanyInfo
import com.pd.currencyconverter.dataclass.Email
import com.pd.currencyconverter.dataclass.Phone

class Converters {
    @TypeConverter
    fun listToJson(value: List<Email>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Email>::class.java).toList()

    @TypeConverter
    fun listToJsonPhone(value: List<Phone>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListPhone(value: String) = Gson().fromJson(value, Array<Phone>::class.java).toList()

    @TypeConverter
    fun listToJsonCompanyInfo(value: CompanyInfo?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListCompanyInfo(value: String) = Gson().fromJson(value, CompanyInfo::class.java)

    @TypeConverter
    fun listToJsonCard(value: Card?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListCard(value: String) = Gson().fromJson(value, Card::class.java)

}