package com.pd.currencyconverter.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pd.currencyconverter.utils.ConstantUtils
import java.io.Serializable

@Entity(tableName = ConstantUtils.TABLE_NAME_ALARM_INFORMATION)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val date: String,
    val time: String,
    val description: String,
) : Serializable
