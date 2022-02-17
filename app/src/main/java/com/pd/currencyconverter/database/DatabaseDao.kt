package com.pd.currencyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.utils.ConstantUtils

@Dao
interface DatabaseDao {
    @Query(ConstantUtils.SELECT_QUERY_FOR_ALL_CARDS)
    fun getAllEmployees(): MutableList<EmployeeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employee: MutableList<EmployeeEntity>?)

    @Query(ConstantUtils.SELECT_QUERY_FOR_ALL_ALARM)
    fun getAllAlarms(): MutableList<AlarmEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: AlarmEntity?)

    @Query(ConstantUtils.DELETE_FROM_ALARM_INFORMATION)
    fun deleteAlarm(id: Int)
//
//    @Delete
//    fun deleteEmployee(employee: EmployeeEntity?)
//
//    @Update
//    fun updateEmployee(employee: EmployeeEntity?)

}