package com.pd.currencyconverter.database

import androidx.room.*
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.utils.ConstantUtils

@Dao
interface EmployeeDao {
    @Query(ConstantUtils.SELECT_QUERY_FOR_ALL_CARDS)
    fun getAllEmployees(): List<EmployeeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employee: List<EmployeeEntity>?)

//    @Insert
//    fun insertEmployee(employee: EmployeeEntity?)
//
//    @Delete
//    fun deleteEmployee(employee: EmployeeEntity?)
//
//    @Update
//    fun updateEmployee(employee: EmployeeEntity?)

}