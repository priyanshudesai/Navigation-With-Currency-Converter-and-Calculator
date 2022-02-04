package com.pd.currencyconverter.database

import androidx.room.*
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.utils.ConstantUtils

@Dao
interface DatabaseDao {
    @Query(ConstantUtils.SELECT_QUERY_FOR_ALL_CARDS)
    fun getAllEmployees(): List<EmployeeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employee: List<EmployeeEntity>?)

    @Query(ConstantUtils.SELECT_QUERY_FOR_ALL_ALARM)
    fun getAllAlarms(): List<AlarmEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: AlarmEntity?)

    @Query("DELETE FROM alarmInformation WHERE id = :id")
    fun deleteAlarm(id: Int)
//
//    @Delete
//    fun deleteEmployee(employee: EmployeeEntity?)
//
//    @Update
//    fun updateEmployee(employee: EmployeeEntity?)

}