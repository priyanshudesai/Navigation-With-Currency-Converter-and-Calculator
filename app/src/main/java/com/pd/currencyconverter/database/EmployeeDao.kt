package com.pd.currencyconverter.database

import androidx.room.*
import com.pd.currencyconverter.dataclass.EmployeeEntity

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM cardInformation ")
    fun getAllEmployees():List<EmployeeEntity>?

    @Insert
    fun insertEmployee(employee: EmployeeEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employee: List<EmployeeEntity>?)

    @Delete
    fun deleteEmployee(employee: EmployeeEntity?)

    @Update
    fun updateEmployee(employee: EmployeeEntity?)

}